package hallow.vessel.soul;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import hallow.vessel.component.ModComponents;
import hallow.vessel.network.payload.SignSoulContractPayload;

@Environment(EnvType.CLIENT)
public class SoulContractScreen extends Screen {
	private final PlayerEntity player;
    private final ItemStack itemStack;
	private ButtonWidget doneButton;
	private ButtonWidget signButton;
	private final Hand hand;
	@Nullable
	private SoulContractScreen.PageContent pageContent = SoulContractScreen.PageContent.EMPTY;
    static ContractState state;
	static boolean canSign;
    private final Text CONTRACT_TEXT_BASE;
    private final Text CONTRACT_TEXT_SIGNED;
    private final Text CONTRACT_TEXT_ACTIVE;
   

	public SoulContractScreen(PlayerEntity player, ItemStack itemStack, Hand hand) {
		super(NarratorManager.EMPTY);
		this.player = player;
		this.itemStack = itemStack;
		this.hand = hand;
        if(itemStack.getOrDefault(ModComponents.ACTIVE, false))
            state = ContractState.ACTIVE;
        else if(itemStack.contains(ModComponents.SOUL_UUID))
            state = ContractState.SIGNED;
        else state = ContractState.BASE;
        
        CONTRACT_TEXT_BASE = Text.translatable("item.vessel.soul_contract.contract_text.base", player.getNameForScoreboard());
        CONTRACT_TEXT_SIGNED = Text.translatable("item.vessel.soul_contract.contract_text.signed", player.getNameForScoreboard());
        CONTRACT_TEXT_ACTIVE = Text.translatable("item.vessel.soul_contract.contract_text.active", player.getNameForScoreboard());

		canSign = !SoulManager.isSoulBound(player) && state == ContractState.BASE;
	}

	//@Override
	//public void tick() {
	//	super.tick();
	//}

	@Override
	protected void init() {
		this.invalidatePageContent();
		this.signButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("book.signButton"), button -> {
			player.sendMessage(Text.literal("signed!"));
            this.signContract();
		}).dimensions(this.width / 2 - 100, 196, 98, 20).build());
		this.doneButton = this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
			this.client.setScreen(null);
		}).dimensions(this.width / 2 + 2, 196, 98, 20).build());
		this.updateButtons();
	}

    private Text getContractText() {
        switch (state) {
            case BASE:
                return CONTRACT_TEXT_BASE;
            case SIGNED:
                return CONTRACT_TEXT_SIGNED;
            case ACTIVE:
                return CONTRACT_TEXT_ACTIVE;
            default:
                return CONTRACT_TEXT_BASE;
        }
    }

	private void updateButtons() {
		this.doneButton.visible = true;
		this.signButton.visible = canSign;
		if(!signButton.visible)
			doneButton.setPosition(width / 2 - 51, 196);
	}

	private void signContract() {
		ClientPlayNetworking.send(new SignSoulContractPayload(hand));
		SoulManager.setSoulBound(player, true);
        this.close();
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.setFocused(null);
		
		SoulContractScreen.PageContent pageContent = this.getPageContent();

		for (SoulContractScreen.Line line : pageContent.lines) {
			context.drawText(this.textRenderer, line.text, line.x, line.y, Colors.BLACK, false);
		}

		this.drawSelection(context, pageContent.selectionRectangles);

	}

	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderInGameBackground(context);
		context.drawTexture(BookScreen.BOOK_TEXTURE, (this.width - 192) / 2, 2, 0, 0, 192, 192);
	}


	private void drawSelection(DrawContext context, Rect2i[] selectionRectangles) {
		for (Rect2i rect2i : selectionRectangles) {
			int i = rect2i.getX();
			int j = rect2i.getY();
			int k = i + rect2i.getWidth();
			int l = j + rect2i.getHeight();
			context.fill(RenderLayer.getGuiTextHighlight(), i, j, k, l, Colors.BLUE);
		}
	}

	private SoulContractScreen.Position screenPositionToAbsolutePosition(SoulContractScreen.Position position) {
		return new SoulContractScreen.Position(position.x - (this.width - 192) / 2 - 36, position.y - 32);
	}

	private SoulContractScreen.Position absolutePositionToScreenPosition(SoulContractScreen.Position position) {
		return new SoulContractScreen.Position(position.x + (this.width - 192) / 2 + 36, position.y + 32);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}

	private SoulContractScreen.PageContent getPageContent() {
		if (this.pageContent == null) {
			this.pageContent = this.createPageContent();
		}

		return this.pageContent;
	}

	private void invalidatePageContent() {
		this.pageContent = null;
	}

	private SoulContractScreen.PageContent createPageContent() {

        int textX = 36;
        int textY = 32 - 13;
        int maxWidth = 114;

        List<SoulContractScreen.Line> lines = new ArrayList<>();

        int y = textY;
        int bookX = (this.width - 192) / 2;

        for (var orderedText : this.textRenderer.wrapLines(getContractText(), maxWidth)) {

            lines.add( new SoulContractScreen.Line(orderedText, bookX + textX, y));
            y += 9;
        }

        return new SoulContractScreen.PageContent(getContractText().getString(), new Position(0, 0), true, new int[]{0}, lines.toArray(new SoulContractScreen.Line[0]), new Rect2i[0]);
    }

	static int getLineFromOffset(int[] lineStarts, int position) {
		int i = Arrays.binarySearch(lineStarts, position);
		return i < 0 ? -(i + 2) : i;
	}

	private Rect2i getRectFromCorners(SoulContractScreen.Position start, SoulContractScreen.Position end) {
		SoulContractScreen.Position position = this.absolutePositionToScreenPosition(start);
		SoulContractScreen.Position position2 = this.absolutePositionToScreenPosition(end);
		int i = Math.min(position.x, position2.x);
		int j = Math.max(position.x, position2.x);
		int k = Math.min(position.y, position2.y);
		int l = Math.max(position.y, position2.y);
		return new Rect2i(i, k, j - i, l - k);
	}

	@Environment(EnvType.CLIENT)
	static class Line {
        final OrderedText text;
        final int x;
        final int y;

        public Line(OrderedText text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }

	@Environment(EnvType.CLIENT)
	static class PageContent {
		static final SoulContractScreen.PageContent EMPTY = new SoulContractScreen.PageContent(
			"", new SoulContractScreen.Position(0, 0), true, new int[]{0}, new SoulContractScreen.Line[]{new SoulContractScreen.Line(OrderedText.EMPTY, 0, 0)}, new Rect2i[0]
		);
		private final String pageContent;
		final SoulContractScreen.Position position;
		final boolean atEnd;
		private final int[] lineStarts;
		final SoulContractScreen.Line[] lines;
		final Rect2i[] selectionRectangles;

		public PageContent(
			String pageContent, SoulContractScreen.Position position, boolean atEnd, int[] lineStarts, SoulContractScreen.Line[] lines, Rect2i[] selectionRectangles
		) {
			this.pageContent = pageContent;
			this.position = position;
			this.atEnd = atEnd;
			this.lineStarts = lineStarts;
			this.lines = lines;
			this.selectionRectangles = selectionRectangles;
		}
	}

	@Environment(EnvType.CLIENT)
	static class Position {
		public final int x;
		public final int y;

		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
