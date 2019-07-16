package net.dyeo.xpdiary.tileentity

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.block.BlockDiary
import net.dyeo.xpdiary.network.XPBalanceMessage
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraftforge.fml.common.network.NetworkRegistry
import java.util.*

class TileEntityDiary : TileEntity(), ITickable, IInventory
{
    var tickCount: Int = 0
    var pageFlip: Float = 0.toFloat()
    var pageFlipPrev: Float = 0.toFloat()
    var flipT: Float = 0.toFloat()
    var flipA: Float = 0.toFloat()
    var bookSpread: Float = 0.toFloat()
    var bookSpreadPrev: Float = 0.toFloat()
    var bookRotation: Float = 0.toFloat()
    var bookRotationPrev: Float = 0.toFloat()
    var tRot: Float = 0.toFloat()
    private val rand = Random()

    var balance = 0.0f
        set(value)
        {
            field = kotlin.math.max(0.0f, value)
        }

    var customName: String? = null

    override fun readFromNBT(compound: NBTTagCompound)
    {
        super.readFromNBT(compound)

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName")
        }

        balance = compound.getFloat("balance")
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound
    {
        if (this.hasCustomName())
        {
            compound.setString("CustomName", customName!!)
        }

        compound.setFloat("balance", balance)

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

    override fun update()
    {
        this.bookSpreadPrev = this.bookSpread
        this.bookRotationPrev = this.bookRotation
        val entityplayer = this.world.getClosestPlayer((this.pos.x.toFloat() + 0.5f).toDouble(), (this.pos.y.toFloat() + 0.5f).toDouble(), (this.pos.z.toFloat() + 0.5f).toDouble(), 3.0, false)

        if (entityplayer != null)
        {
            val d0 = entityplayer.posX - (this.pos.x.toFloat() + 0.5f).toDouble()
            val d1 = entityplayer.posZ - (this.pos.z.toFloat() + 0.5f).toDouble()
            this.tRot = MathHelper.atan2(d1, d0).toFloat()
            this.bookSpread += 0.1f

            if (this.bookSpread < 0.5f || rand.nextInt(40) == 0)
            {
                val f1 = this.flipT

                while (true)
                {
                    this.flipT += (rand.nextInt(4) - rand.nextInt(4)).toFloat()

                    if (f1 != this.flipT)
                    {
                        break
                    }
                }
            }
        }
        else
        {
            this.tRot += 0.02f
            this.bookSpread -= 0.1f
        }

        while (this.bookRotation >= Math.PI.toFloat())
        {
            this.bookRotation -= Math.PI.toFloat() * 2f
        }

        while (this.bookRotation < -Math.PI.toFloat())
        {
            this.bookRotation += Math.PI.toFloat() * 2f
        }

        while (this.tRot >= Math.PI.toFloat())
        {
            this.tRot -= Math.PI.toFloat() * 2f
        }

        while (this.tRot < -Math.PI.toFloat())
        {
            this.tRot += Math.PI.toFloat() * 2f
        }

        var f2: Float

        f2 = this.tRot - this.bookRotation
        while (f2 >= Math.PI.toFloat())
        {
            f2 -= Math.PI.toFloat() * 2f
        }

        while (f2 < -Math.PI.toFloat())
        {
            f2 += Math.PI.toFloat() * 2f
        }

        this.bookRotation += f2 * 0.4f
        this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0f, 1.0f)
        ++this.tickCount
        this.pageFlipPrev = this.pageFlip
        var f = (this.flipT - this.pageFlip) * 0.4f
        val f3 = 0.2f
        f = MathHelper.clamp(f, -0.2f, 0.2f)
        this.flipA += (f - this.flipA) * 0.9f
        this.pageFlip += this.flipA
    }

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

    val storagePower: Float
    get()
    {
        var power = 0.0f
        for(j in -1..1)
        {
            for(k in -1..1)
            {
                if((j != 0 || k != 0) && world.isAirBlock(pos.add(k,0,j)) && world.isAirBlock(pos.add(k,1,j)))
                {
                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k*2,0,j*2))
                    power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k*2,1,j*2))
                    if (k != 0 && j != 0)
                    {
                        power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 0, j))
                        power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k * 2, 1, j))
                        power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k, 0, j * 2))
                        power += net.minecraftforge.common.ForgeHooks.getEnchantPower(world, pos.add(k, 1, j * 2))
                    }
                }
            }
        }
        return kotlin.math.min(15.0f, power)
    }

    val storageTax: Float
    get() = (1.0f - (storagePower / 15.0f)) * 0.3f
}