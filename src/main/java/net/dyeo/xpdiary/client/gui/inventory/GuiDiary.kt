package net.dyeo.xpdiary.client.gui.inventory

import net.dyeo.xpdiary.container.ContainerDiary
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import java.awt.Color

class GuiDiary(private val player: EntityPlayer, private val tileEntityDiary: TileEntityDiary) : GuiContainer(ContainerDiary(player, tileEntityDiary))
{
    private val texture = ResourceLocation(modid, "textures/gui/diary.png")
    private val container: ContainerDiary = this.inventorySlots as ContainerDiary

    private val plus1 = GuiButton(0, 4, 138, 40, 20, "▲1")
    private val plus10 = GuiButton(1, 48, 138, 40, 20, "▲10")
    private val plus100 = GuiButton(2, 92, 138, 40, 20, "▲100")
    private val plus1000 = GuiButton(3, 136, 138, 40, 20, "▲1000")

    private val minus1 = GuiButton(4, 4, 160, 40, 20, "▼1")
    private val minus10 = GuiButton(5, 48, 160, 40, 20, "▼10")
    private val minus100 = GuiButton(6, 92, 160, 40, 20, "▼100")
    private val minus1000 = GuiButton(7, 136, 160, 40, 20, "▼1000")

    init
    {
        xSize = 176
        ySize = 133
    }

    override fun initGui()
    {
        super.initGui()

        this.buttonList.add(plus1)
        this.buttonList.add(plus10)
        this.buttonList.add(plus100)
        this.buttonList.add(plus1000)

        this.buttonList.add(minus1)
        this.buttonList.add(minus10)
        this.buttonList.add(minus100)
        this.buttonList.add(minus1000)

        updateUi()
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, x: Int, y: Int)
    {
        Minecraft.getMinecraft().textureManager.bindTexture(texture)
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int)
    {
        updateUi()
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)

        fontRenderer.drawString(tileEntityDiary.displayName!!.unformattedText, 5, 5, Color.darkGray.rgb)

        val xp = "%.2f".format(tileEntityDiary.balance) + " / " + tileEntityDiary.storageCap.toString() + "XP"
        val tax = "-" + "%.2f".format(tileEntityDiary.storageTax*100.0f) + "%"
        val px = player.experienceTotal.toString() + "XP"

        val xpw = fontRenderer.getStringWidth(xp)
        val taxw = fontRenderer.getStringWidth(tax)
        val pxw = fontRenderer.getStringWidth(px)

        fontRenderer.drawString(xp, xSize/2 - xpw/2, 32 + fontRenderer.FONT_HEIGHT/2, Color.darkGray.rgb)
        fontRenderer.drawString(tax, xSize/2 - taxw/2, ySize/2 - 10 - fontRenderer.FONT_HEIGHT/2, Color.darkGray.rgb)
        fontRenderer.drawString(px, xSize/2 - pxw/2, ySize - 7 - fontRenderer.FONT_HEIGHT, Color.darkGray.rgb)

        updateUi()
    }

    override fun actionPerformed(button: GuiButton)
    {
        super.actionPerformed(button)
        this.mc.playerController.sendEnchantPacket(this.container.windowId, button.id)
    }

    private fun updateUi()
    {
        plus1.enabled = player.experienceTotal >= 1 && tileEntityDiary.balance.toInt() + 1 <= tileEntityDiary.storageCap
        plus10.enabled = player.experienceTotal >= 10 && tileEntityDiary.balance.toInt() + 10 <= tileEntityDiary.storageCap
        plus100.enabled = player.experienceTotal >= 100 && tileEntityDiary.balance.toInt() + 100 <= tileEntityDiary.storageCap
        plus1000.enabled = player.experienceTotal >= 1000 && tileEntityDiary.balance.toInt() + 1000 <= tileEntityDiary.storageCap

        minus1.enabled = tileEntityDiary.balance >= 1
        minus10.enabled = tileEntityDiary.balance >= 10
        minus100.enabled = tileEntityDiary.balance >= 100
        minus1000.enabled = tileEntityDiary.balance >= 1000

        updateButtonPositions()
    }

    private fun updateButtonPositions()
    {
        val ofs = 2
        var xpos = guiLeft + (ofs * 2) + 1

        for (button in buttonList.take(4))
        {
            button.x = xpos
            button.y = (guiTop + ySize) - 60 - (ofs * 3)
            xpos += ofs + button.width
        }

        xpos = guiLeft + (ofs * 2) + 1

        for (button in buttonList.drop(4))
        {
            button.x = xpos
            button.y = (guiTop + ySize) - 40 - (ofs * 2)
            xpos += ofs + button.width
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float)
    {
        this.drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}