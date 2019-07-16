package net.dyeo.xpdiary.container

import net.dyeo.xpdiary.network.XPBalanceMessageHandler
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.dyeo.xpdiary.utility.XPHelper
import net.dyeo.xpdiary.utility.experienceValues
import net.minecraft.block.BlockEnchantmentTable
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.tileentity.TileEntityEnchantmentTable

class ContainerDiary(private val player: EntityPlayer, private val tileEntityDiary: TileEntityDiary) : Container()
{
    companion object
    {
        init
        {
            XPBalanceMessageHandler.registerMessage()
        }
    }

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
        val xp = XPHelper(player.experienceTotal - amount)
        tileEntityDiary.balance += diaryAmount
        tileEntityDiary.updateBalance()
        player.experienceValues = xp
    }
}