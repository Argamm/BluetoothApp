package com.zdravnica.uikit.components.chips.models

import com.zdravnica.uikit.resources.R

sealed class BigChipType {
    abstract val chipData: BigChipsStateModel

    data object Skin : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_skin,
            description = R.string.select_product_skin_description,
            iconRes = R.mipmap.ic_skin
        )
    }

    data object Lungs : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_lungs,
            description = R.string.select_product_lungs_description,
            iconRes = R.mipmap.ic_lungs
        )
    }

    data object Heart : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_heart,
            description = R.string.select_product_heart_description,
            iconRes = R.mipmap.ic_heart
        )
    }

    data object Pancreas : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_pancreas,
            description = R.string.select_product_pancreas_description,
            iconRes = R.mipmap.ic_pancreas
        )
    }

    data object Intestine : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_intestine,
            description = R.string.select_product_intestine_description,
            iconRes = R.mipmap.ic_intestine
        )
    }

    data object Uterus : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_uterus,
            description = R.string.select_product_uterus_description,
            iconRes = R.mipmap.ic_uterus
        )
    }

    data object Brain : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_brain,
            description = R.string.select_product_brain_description,
            iconRes = R.mipmap.ic_brain
        )
    }

    data object Stomach : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_stomach,
            description = R.string.select_product_stomach_description,
            iconRes = R.mipmap.ic_stomach
        )
    }

    data object KneeJoint : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_knee_joint,
            description = R.string.select_product_knee_joint_description,
            iconRes = R.mipmap.ic_knee_joint
        )
    }

    data object Nose : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = false,
            title = R.string.select_product_nose,
            description = R.string.select_product_nose_description,
            iconRes = R.mipmap.ic_nose
        )
    }

    data object CustomMix : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_custom_mix,
            description = R.string.select_product_custom_mix_description,
            iconRes = null
        )
    }

    data object WithoutBalm : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_without_balm,
            description = R.string.select_product_without_balm_description,
            iconRes = null
        )
    }
}

fun getChipDataList(): List<BigChipType> = listOf(
    BigChipType.Skin,
    BigChipType.Lungs,
    BigChipType.Heart,
    BigChipType.Pancreas,
    BigChipType.Intestine,
    BigChipType.Uterus,
    BigChipType.Brain,
    BigChipType.Stomach,
    BigChipType.KneeJoint,
    BigChipType.Nose,
    BigChipType.CustomMix,
    BigChipType.WithoutBalm
)