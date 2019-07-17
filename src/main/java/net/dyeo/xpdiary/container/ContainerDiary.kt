package net.dyeo.xpdiary.container

import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.dyeo.xpdiary.utility.XPHelper
import net.dyeo.xpdiary.utility.experienceValues
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container

class ContainerDiary(private val player: EntityPlayer, private val tileEntityDiary: TileEntityDiary) : Container()
{
    override fun canInteractWith(playerIn: EntityPlayer): Boolean = tileEntityDiary.isUsableByPlayer(playerIn)

    override fun onContainerClosed(playerIn: EntityPlayer)
    {
        super.onContainerClosed(playerIn)
        tileEntityDiary.closeInventory(playerIn)
    }

    override fun enchantItem(playerIn: EntityPlayer, id: Int): Boolean
    {
        super.enchantItem(playerIn, id)

        var result = true
        when (id)
        {
            0 -> adjustExperience(player, 1)
            1 -> adjustExperience(player, 10)
            2 -> adjustExperience(player, 100)
            3 -> adjustExperience(player, 1000)
            4 -> adjustExperience(player, -1)
            5 -> adjustExperience(player, -10)
            6 -> adjustExperience(player, -100)
            7 -> adjustExperience(player, -1000)
            else -> result = false
        }
        return result
    }

    private fun adjustExperience(player: EntityPlayer, amount: Int)
    {
        val diaryAmount = if(amount > 0) amount.toFloat() * (1.0f - tileEntityDiary.storageTax) else amount.toFloat()
        var playerAmount = if(diaryAmount > tileEntityDiary.storageCap) tileEntityDiary.storageCap.toFloat() - tileEntityDiary.balance else amount.toFloat()

        tileEntityDiary.balance = kotlin.math.min(tileEntityDiary.balance + diaryAmount, tileEntityDiary.storageCap.toFloat())
        tileEntityDiary.updateBalance()

        val xp = XPHelper(player.experienceTotal - playerAmount.toInt())
        player.experienceValues = xp
    }
}