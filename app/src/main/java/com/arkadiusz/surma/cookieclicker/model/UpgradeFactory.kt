package com.arkadiusz.surma.cookieclicker.model

class UpgradeFactory {
    companion object {
        fun create(): List<TypeUpgrades> {
            val upgrades: MutableList<TypeUpgrades> = mutableListOf()
            upgrades.add(
                0, TypeUpgrades(
                    Upgrade.TYPE_CLICK, listOf<Upgrade>(
                        Upgrade(30, "Work harder", Upgrade.TYPE_CLICK, 1, 1),
                        Upgrade(120, "Work even harder", Upgrade.TYPE_CLICK, 2, 5),
                        Upgrade(1000, "Work with your family", Upgrade.TYPE_CLICK, 3, 10)
                    )
                )
            )
            upgrades.add(
                1, TypeUpgrades(
                    Upgrade.TYPE_COST, listOf<Upgrade>(
                        Upgrade(34, "Hire younger brother", Upgrade.TYPE_COST, 1, 90),
                        Upgrade(130, "Hire his friends :)", Upgrade.TYPE_COST, 2, 70),
                        Upgrade(6000, "Work with your family", Upgrade.TYPE_COST, 3, 50)
                    )
                )
            )
            upgrades.add(
                2, TypeUpgrades(
                    Upgrade.TYPE_TIME, listOf<Upgrade>(
                        Upgrade(20, "Build work robot", Upgrade.TYPE_TIME, 1, 1),
                        Upgrade(90, "Incept your work", Upgrade.TYPE_TIME, 2, 4),
                        Upgrade(4000, "Incept it deeper", Upgrade.TYPE_TIME, 3, 30)
                    )
                )
            )
            return upgrades.toList()
        }
    }
}