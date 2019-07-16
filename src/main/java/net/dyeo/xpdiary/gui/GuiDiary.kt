package net.dyeo.xpdiary.gui

import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.container.ContainerDiary
import net.dyeo.xpdiary.modid
import net.dyeo.xpdiary.network.XPBalanceMessageHandler
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
    companion object
    {
        init
        {
            XPBalanceMessageHandler.registerMessage()
        }
    }

    private val texture = ResourceLocation(modid, "textures/gui/${BlockDiary.name}.png")
    private val container: ContainerDiary = this.inventorySlots as ContainerDiary

    private val plus1 = GuiButton(0, 220, 60, 60, 20, "+1")
    private val plus10 = GuiButton(1, 220, 80, 60, 20, "+10")
    private val plus100 = GuiButton(2, 220, 100, 60, 20, "+100")
    private val plus1000 = GuiButton(3, 220, 120, 60, 20, "+1000")

    private val minus1 = GuiButton(4, 160, 60, 60, 20, "-1")
    private val minus10 = GuiButton(5, 160, 80, 60, 20, "-10")
    private val minus100 = GuiButton(6, 160, 100, 60, 20, "-100")
    private val minus1000 = GuiButton(7, 160, 120, 60, 20, "-1000")

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

        val LABEL_XPOS = 5
        val LABEL_YPOS = 5
        fontRenderer.drawString(tileEntityDiary.displayName!!.unformattedText, LABEL_XPOS, LABEL_YPOS, Color.darkGray.rgb)
        fontRenderer.drawString("${tileEntityDiary.balance}XP", LABEL_XPOS, LABEL_YPOS + 10, Color.darkGray.rgb)
        fontRenderer.drawString("-${tileEntityDiary.storageTax*100}%", LABEL_XPOS, LABEL_YPOS + 20, Color.darkGray.rgb)
    }

    override fun actionPerformed(button: GuiButton)
    {
        super.actionPerformed(button)
        this.mc.playerController.sendEnchantPacket(this.container.windowId, button.id)
    }

    private fun updateUi()
    {
        plus1.enabled = player.experienceTotal >= 1
        plus10.enabled = player.experienceTotal >= 10
        plus100.enabled = player.experienceTotal >= 100
        plus1000.enabled = player.experienceTotal >= 1000

        minus1.enabled = tileEntityDiary.balance >= 1
        minus10.enabled = tileEntityDiary.balance >= 10
        minus100.enabled = tileEntityDiary.balance >= 100
        minus1000.enabled = tileEntityDiary.balance >= 1000
    }

}