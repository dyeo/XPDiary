package net.dyeo.xpdiary.renderer

import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.client.model.ModelBook
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntityEnchantmentTable
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class TileEntityDiaryRenderer : TileEntitySpecialRenderer<TileEntityDiary>()
{
    companion object
    {
        private val TEXTURE_BOOK = ResourceLocation("textures/entity/enchanting_table_book.png")
    }

    private val modelBook = ModelBook()

    override fun render(te: TileEntityDiary, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float)
    {
        GlStateManager.pushMatrix()
        GlStateManager.translate(x.toFloat() + 0.5f, y.toFloat() + 0.75f, z.toFloat() + 0.5f)
        val f = te.tickCount.toFloat() + partialTicks
        GlStateManager.translate(0.0f, 0.1f + MathHelper.sin(f * 0.1f) * 0.01f, 0.0f)
        var f1: Float

        f1 = te.bookRotation - te.bookRotationPrev
        while (f1 >= Math.PI.toFloat())
        {
            f1 -= Math.PI.toFloat() * 2f
        }

        while (f1 < -Math.PI.toFloat())
        {
            f1 += Math.PI.toFloat() * 2f
        }

        val f2 = te.bookRotationPrev + f1 * partialTicks
        GlStateManager.rotate(-f2 * (180f / Math.PI.toFloat()), 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(80.0f, 0.0f, 0.0f, 1.0f)
        this.bindTexture(TEXTURE_BOOK)
        var f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25f
        var f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75f
        f3 = (f3 - MathHelper.fastFloor(f3.toDouble()).toFloat()) * 1.6f - 0.3f
        f4 = (f4 - MathHelper.fastFloor(f4.toDouble()).toFloat()) * 1.6f - 0.3f

        if (f3 < 0.0f)
        {
            f3 = 0.0f
        }

        if (f4 < 0.0f)
        {
            f4 = 0.0f
        }

        if (f3 > 1.0f)
        {
            f3 = 1.0f
        }

        if (f4 > 1.0f)
        {
            f4 = 1.0f
        }

        val f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks
        GlStateManager.enableCull()
        this.modelBook.render(null, f, f3, f4, f5, 0.0f, 0.0625f)
        GlStateManager.popMatrix()
    }
}