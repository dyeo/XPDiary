package net.dyeo.xpdiary.tileentity

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.network.XPBalanceMessage
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraftforge.fml.common.network.NetworkRegistry

class TileEntityDiary : TileEntity(), IInventory
{
    var balance = 0
        set(value)
        {
            field = kotlin.math.max(0, value)
        }

    var customName: String? = null

    override fun readFromNBT(compound: NBTTagCompound)
    {
        super.readFromNBT(compound)

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName")
        }

        balance = compound.getInteger("balance")
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound
    {
        if (this.hasCustomName())
        {
            compound.setString("CustomName", customName!!)
        }

        compound.setInteger("balance", balance)

        return super.writeToNBT(compound)
    }

    override fun getField(id: Int): Int = 0

    override fun hasCustomName(): Boolean = customName != null && customName?.isEmpty() == false

    override fun getDisplayName(): ITextComponent? = if (this.hasCustomName()) TextComponentString(customName!!) else TextComponentTranslation(this.name)

    override fun getStackInSlot(index: Int): ItemStack = ItemStack.EMPTY

    override fun decrStackSize(index: Int, count: Int): ItemStack = ItemStack.EMPTY

    override fun clear() {}

    override fun getSizeInventory(): Int = 0

    override fun getName(): String = if (hasCustomName()) customName!! else "container.${BlockDiary.name}.name"

    override fun isEmpty(): Boolean = true

    override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean = false

    override fun getInventoryStackLimit(): Int = 0

    override fun isUsableByPlayer(player: EntityPlayer): Boolean
    {
        if (this.world.getTileEntity(this.pos) !== this) return false
        val CENTRE_OFFSET = 0.5
        val MAXIMUM_DISTANCE_SQ = 8.0 * 8.0
        return player.getDistanceSq(pos.x + CENTRE_OFFSET, pos.y + CENTRE_OFFSET, pos.z + CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ
    }

    override fun openInventory(player: EntityPlayer) {}

    override fun setField(id: Int, value: Int) {}

    override fun closeInventory(player: EntityPlayer) = markDirty()

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {}

    override fun removeStackFromSlot(index: Int): ItemStack = ItemStack.EMPTY

    override fun getFieldCount(): Int = 0

    fun updateBalance()
    {
        XPDiary.networkChannel.sendToAllTracking(XPBalanceMessage(pos, balance),
                NetworkRegistry.TargetPoint(
                        world.provider.dimension,
                        pos.x.toDouble(),
                        pos.x.toDouble(),
                        pos.x.toDouble(),
                        0.0
                )
        )
    }

}