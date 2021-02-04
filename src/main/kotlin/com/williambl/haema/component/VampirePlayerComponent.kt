package com.williambl.haema.component

import com.williambl.haema.VampireAbility
import dev.onyxstudios.cca.api.v3.component.CopyableComponent
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import nerdhub.cardinal.components.api.ComponentType
import net.minecraft.nbt.CompoundTag

@Suppress("UnstableApiUsage")
class VampirePlayerComponent : VampireComponent, AutoSyncedComponent, CopyableComponent<VampirePlayerComponent> {
    override var isVampire: Boolean = false
    override var isPermanentVampire: Boolean = false
    override var isKilled: Boolean = false

    override var abilities: MutableMap<VampireAbility, Int> = mutableMapOf(
        VampireAbility.STRENGTH to 3,
        VampireAbility.DASH to 1,
        VampireAbility.INVISIBILITY to 1,
        VampireAbility.IMMORTALITY to 1,
        VampireAbility.VISION to 1
    )

    override fun writeToNbt(tag: CompoundTag) {
        tag.putBoolean("isVampire", isVampire)
        tag.putBoolean("isPermanentVampire", isPermanentVampire)
        tag.putBoolean("isKilled", isKilled)
        tag.put("abilities", CompoundTag().also { abilitiesTag -> abilities.forEach { (ability, value) -> abilitiesTag.putInt(ability.name, value) } })
    }

    override fun readFromNbt(tag: CompoundTag) {
        isVampire = tag.getBoolean("isVampire")
        isPermanentVampire = tag.getBoolean("isPermanentVampire")
        isKilled = tag.getBoolean("isKilled")
        val abilitiesTag = tag.getCompound("abilities")
        VampireAbility.values().filter { abilitiesTag.contains(it.name) }.forEach { abilities[it] = abilitiesTag.getInt(it.name) }
    }

    override fun copyFrom(other: VampirePlayerComponent) {
        isVampire = other.isVampire
        isPermanentVampire = other.isPermanentVampire
        abilities = other.abilities
    }

    override fun getComponentType(): ComponentType<*> {
        throw UnsupportedOperationException()
    }

}