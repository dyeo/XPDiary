package net.dyeo.xpdiary.gui

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

import java.util.HashMap

object GuiHandlerRegistry : IGuiHandler
{
    private val registeredHandlers = HashMap<Int, IGuiHandler>()

    fun registerGuiHandler(handler: IGuiHandler, guiID: Int)
    {
        registeredHandlers[guiID] = handler
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        val handler = registeredHandlers[ID]
        return handler?.getServerGuiElement(ID, player, world, x, y, z)
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any?
    {
        val handler = registeredHandlers[ID]
        return handler?.getClientGuiElement(ID, player, world, x, y, z)
    }

}
