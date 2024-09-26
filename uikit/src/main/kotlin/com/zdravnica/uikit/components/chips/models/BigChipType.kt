package com.zdravnica.uikit.components.chips.models

import com.zdravnica.uikit.resources.R

sealed class BigChipType {
    abstract val chipData: BigChipsStateModel
    abstract val chipBalmInfoList: List<ChipBalmInfoModel>?

    data object Skin : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_skin,
            description = R.string.select_product_skin_description,
            iconRes = R.mipmap.ic_skin
        )

        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_mint, false, 30),
            ChipBalmInfoModel(R.string.menu_screen_nut, true, 10)
        )
    }

    data object Lungs : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_lungs,
            description = R.string.select_product_lungs_description,
            iconRes = R.mipmap.ic_lungs
        )

        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, true, 14),
            ChipBalmInfoModel(R.string.menu_screen_mint, false, 13),
            ChipBalmInfoModel(R.string.menu_screen_nut, true, 13)
        )
    }

    data object Heart : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_heart,
            description = R.string.select_product_heart_description,
            iconRes = R.mipmap.ic_heart
        )

        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, false, 13),
            ChipBalmInfoModel(R.string.menu_screen_mint, false, 7),
            ChipBalmInfoModel(R.string.menu_screen_nut, false, 20)
        )
    }

    data object Intestine : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_intestine,
            description = R.string.select_product_intestine_description,
            iconRes = R.mipmap.ic_intestine
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, false, 8),
            ChipBalmInfoModel(R.string.menu_screen_mint, false, 24),
            ChipBalmInfoModel(R.string.menu_screen_nut, false, 8)
        )
    }

    data object Uterus : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_uterus,
            description = R.string.select_product_uterus_description,
            iconRes = R.mipmap.ic_uterus
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, false, 10),
            ChipBalmInfoModel(R.string.menu_screen_mint, false, 20),
            ChipBalmInfoModel(R.string.menu_screen_nut, false, 10)
        )
    }

    data object Brain : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_brain,
            description = R.string.select_product_brain_description,
            iconRes = R.mipmap.ic_brain
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, true, 16),
            ChipBalmInfoModel(R.string.menu_screen_mint, true, 16),
            ChipBalmInfoModel(R.string.menu_screen_nut, true, 8)
        )
    }

    data object KneeJoint : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_knee_joint,
            description = R.string.select_product_knee_joint_description,
            iconRes = R.mipmap.ic_knee_joint
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, true, 26),
            ChipBalmInfoModel(R.string.menu_screen_nut, true, 14)
        )
    }

    data object Nose : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_nose,
            description = R.string.select_product_nose_description,
            iconRes = R.mipmap.ic_nose//will be replaced
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, true, 5),
            ChipBalmInfoModel(R.string.menu_screen_mint, true, 20),
            ChipBalmInfoModel(R.string.menu_screen_nut, true, 15)
        )
    }

    data object CustomMix : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_custom_mix,
            description = R.string.select_product_custom_mix_description,
            iconRes = null
        )
        override val chipBalmInfoList = null
    }

    data object WithoutBalm : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_without_balm,
            description = R.string.select_product_without_balm_description,
            iconRes = null
        )
        override val chipBalmInfoList = null
    }

    companion object {
        fun getChipDataByTitle(title: Int): BigChipsStateModel? {
            return getChipDataList().find { it.chipData.title == title }?.chipData
        }

        fun getBalmInfoByTitle(title: Int): List<ChipBalmInfoModel>? {
            return getChipDataList().find { it.chipData.title == title }?.chipBalmInfoList
        }

        fun getAllChipTitles(): List<Int> {
            return getChipDataList().map { it.chipData.title }
        }

        fun getChipDataList(): List<BigChipType> = listOf(
            Skin,
            Lungs,
            Heart,
            Intestine,
            Uterus,
            Brain,
            KneeJoint,
            Nose,
            CustomMix,
            WithoutBalm
        )
    }
}