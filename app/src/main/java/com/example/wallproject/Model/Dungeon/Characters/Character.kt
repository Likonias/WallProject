package com.example.wallproject.Model.Dungeon.Characters

import kotlin.random.Random

abstract class Character{

    abstract var name : String
    abstract var health : Double
    abstract var attack : Attack
    abstract var defense : Defense

    //transient is exlucing values of being serialized by the GSON class for JSON
    @Transient private val defenseThreshold = 0.8
    @Transient private val criticalMultiplier = 1.5
    @Transient private val hundred = 100.0
    @Transient private val zero = 0.0

    fun isDead(): Boolean {
        return (health <= zero)
    }

    fun attack(character: Character) : Boolean{

        var trueAttack = calculateAttackTrue(character)

        if(isCritical(trueAttack.luck)){
            trueAttack.attackDmg = trueAttack.attackDmg * criticalMultiplier
            trueAttack.abilityPower = trueAttack.abilityPower * criticalMultiplier
        }

        character.health = character.health - (trueAttack.attackDmg + trueAttack.abilityPower)

        return character.isDead()

    }

    private fun calculateDefensePercentage(character: Character) : Defense {

        var resultArmor = ruleOfThree(character.health, hundred, character.defense.armor) / hundred

        var resultMagicResist = ruleOfThree(character.health, hundred, character.defense.magicResist) / hundred

        if(resultArmor > defenseThreshold)
            resultArmor = defenseThreshold

        if (resultMagicResist > defenseThreshold)
            resultMagicResist = defenseThreshold

        return Defense(resultArmor, resultMagicResist)

    }

    private fun calculateLuckPercentage() : Double {
        return ruleOfThree(this.health, hundred, this.attack.luck)
    }

    private fun calculateAttackTrue(character: Character) : Attack {

        var resultDefense = calculateDefensePercentage(character)

        var resultAttack : Attack = Attack(zero, zero, zero)

        resultAttack.attackDmg = this.attack.attackDmg * resultDefense.armor

        resultAttack.abilityPower = this.attack.abilityPower * resultDefense.magicResist

        resultAttack.luck = calculateLuckPercentage()

        return resultAttack

    }

    //function calculates chance for a critical strike
    private fun isCritical (luck : Double) : Boolean {

        var result = Random.nextDouble(hundred + 1)

        return (result < luck)

    }

    private fun ruleOfThree(a : Double, b : Double, c : Double) : Double {

        return b * c / a

    }

}
