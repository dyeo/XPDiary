package net.dyeo.xpdiary.utility

import net.minecraft.entity.player.EntityPlayer

class XPHelper(val experienceTotal: Int)
{
    var experienceLevel: Int = 0
        private set

    var experience: Float = 0.0f
        private set

    init
    {
        experience = experienceTotal.toFloat() / xpBarCap.toFloat()

        while (experience >= 1.0f)
        {
            experience = (experience - 1.0f) * xpBarCap.toFloat()
            experienceLevel++
            experience /= this.xpBarCap.toFloat()
        }
    }

    private val xpBarCap: Int
    get()
    {
        return when
        {
            experienceLevel >= 30 -> 112 + (experienceLevel - 30) * 9
            experienceLevel >= 15 -> 37 + (experienceLevel - 15) * 5
            experienceLevel >= 0 -> 7 + experienceLevel * 2
            else -> 0
        }
    }
}

var EntityPlayer.experienceValues: XPHelper
    get()
    {
        return XPHelper(this.experienceTotal)
    }
    set(value)
    {
        this.experienceTotal = value.experienceTotal
        this.experienceLevel = value.experienceLevel
        this.experience = value.experience
    }