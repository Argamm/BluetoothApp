package com.zdravnica.uikit.components.chips.models

import android.content.Context
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
            ChipBalmInfoModel(R.string.menu_screen_nut, 10, 2, 0.5),
            ChipBalmInfoModel(R.string.menu_screen_mint, 30, 3, 1.5)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 14, 1, 0.7),
            ChipBalmInfoModel(R.string.menu_screen_nut, 13, 2, 0.65),
            ChipBalmInfoModel(R.string.menu_screen_mint, 13, 3, 0.65)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 13, 1, 0.65),
            ChipBalmInfoModel(R.string.menu_screen_nut, 20, 2, 1.0),
            ChipBalmInfoModel(R.string.menu_screen_mint, 7, 3, 0.35)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 8, 1, 0.4),
            ChipBalmInfoModel(R.string.menu_screen_nut, 8, 2, 0.4),
            ChipBalmInfoModel(R.string.menu_screen_mint, 24, 3, 1.2)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 10, 1, 0.5),
            ChipBalmInfoModel(R.string.menu_screen_nut, 10, 2, 0.75),
            ChipBalmInfoModel(R.string.menu_screen_mint, 20, 3, 1.0)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 16, 1, 0.8),
            ChipBalmInfoModel(R.string.menu_screen_nut, 8, 2, 0.4),
            ChipBalmInfoModel(R.string.menu_screen_mint, 16, 3, 0.8)
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
            ChipBalmInfoModel(R.string.menu_screen_burdock, 26, 1, 1.3),
            ChipBalmInfoModel(R.string.menu_screen_nut, 14, 2, 0.7)
        )
    }

    data object Nose : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_nose,
            description = R.string.select_product_nose_description,
            iconRes = R.mipmap.ic_nose //will be replaced
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.menu_screen_burdock, 5, 1, 0.25),
            ChipBalmInfoModel(R.string.menu_screen_nut, 15, 2, 0.4),
            ChipBalmInfoModel(R.string.menu_screen_mint, 20, 3, 1.2)
        )
    }

    data object CustomMix : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_custom_mix,
            description = R.string.select_product_custom_mix_description,
            iconRes = null
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.select_product_custom_mix, 20, 4, 1.0),
        )
    }

    data object WithoutBalm : BigChipType() {
        override val chipData = BigChipsStateModel(
            isEnabled = true,
            title = R.string.select_product_without_balm,
            description = R.string.select_product_without_balm_description,
            iconRes = null
        )
        override val chipBalmInfoList = listOf(
            ChipBalmInfoModel(R.string.select_product_without_balm, 0, 5, 0.0),
        )
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

        fun getAllBalmNames(context: Context): List<String> {
            return getChipDataList()
                .flatMap { it.chipBalmInfoList.orEmpty() }
                .map { context.getString(it.balmName) }
                .distinct()
        }
    }
}

fun getSystemTimingSequence(chipTitle: Int): List<List<Pair<ValveType, Long>>> {
    val systemType = when (chipTitle) {
        R.string.select_product_skin -> SystemType.SKIN
        R.string.select_product_heart -> SystemType.CARDIOVASCULAR
        R.string.select_product_knee_joint -> SystemType.MUSCLE_JOINT
        R.string.select_product_brain -> SystemType.NERVOUS_SYSTEM
        R.string.select_product_lungs -> SystemType.RESPIRATORY
        R.string.select_product_uterus -> SystemType.URINARY
        R.string.select_product_nose -> SystemType.IMMUNE_METABOLIC
        R.string.select_product_intestine -> SystemType.DIGESTIVE
        R.string.select_product_custom_mix -> SystemType.INDIVIDUAL
        else -> SystemType.WITHOUT_BALM
    }

    return when (systemType) {
        SystemType.SKIN -> listOf(
            listOf(ValveType.FIRST_BALM to 0L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 15L),
            listOf(ValveType.FIRST_BALM to 0L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 15L),
            listOf(ValveType.FIRST_BALM to 0L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 15L)
        )
        SystemType.CARDIOVASCULAR -> listOf(
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 2L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 2L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 2L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 2L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 2L)
        )
        SystemType.MUSCLE_JOINT -> listOf(
            listOf(ValveType.FIRST_BALM to 13L, ValveType.SECOND_BALM to 7L, ValveType.THIRD_BALM to 0L),
            listOf(ValveType.FIRST_BALM to 13L, ValveType.SECOND_BALM to 7L, ValveType.THIRD_BALM to 0L),
            listOf(ValveType.FIRST_BALM to 13L, ValveType.SECOND_BALM to 7L, ValveType.THIRD_BALM to 0L)
        )
        SystemType.NERVOUS_SYSTEM -> listOf(
            listOf(ValveType.FIRST_BALM to 8L, ValveType.SECOND_BALM to 8L, ValveType.THIRD_BALM to 4L),
            listOf(ValveType.FIRST_BALM to 8L, ValveType.SECOND_BALM to 8L, ValveType.THIRD_BALM to 4L),
            listOf(ValveType.FIRST_BALM to 8L, ValveType.SECOND_BALM to 8L, ValveType.THIRD_BALM to 4L)
        )
        SystemType.RESPIRATORY -> listOf(
            listOf(ValveType.FIRST_BALM to 7L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 6L),
            listOf(ValveType.FIRST_BALM to 7L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 6L),
            listOf(ValveType.FIRST_BALM to 7L, ValveType.SECOND_BALM to 7L, ValveType.THIRD_BALM to 7L)
        )
        SystemType.URINARY -> listOf(
            listOf(ValveType.FIRST_BALM to 5L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 7L),
            listOf(ValveType.FIRST_BALM to 5L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 7L),
            listOf(ValveType.FIRST_BALM to 5L, ValveType.SECOND_BALM to 5L, ValveType.THIRD_BALM to 6L)
        )
        SystemType.IMMUNE_METABOLIC -> listOf(
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 8L, ValveType.THIRD_BALM to 10L),
            listOf(ValveType.FIRST_BALM to 3L, ValveType.SECOND_BALM to 8L, ValveType.THIRD_BALM to 10L),
            listOf(ValveType.FIRST_BALM to 0L, ValveType.SECOND_BALM to 6L, ValveType.THIRD_BALM to 10L)
        )
        SystemType.DIGESTIVE -> listOf(
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 4L, ValveType.THIRD_BALM to 12L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 4L, ValveType.THIRD_BALM to 12L),
            listOf(ValveType.FIRST_BALM to 4L, ValveType.SECOND_BALM to 4L, ValveType.THIRD_BALM to 12L)
        )
        SystemType.INDIVIDUAL -> listOf(
            listOf(ValveType.FOURTH_BALM to 60L)
        )

        SystemType.WITHOUT_BALM -> listOf(
            listOf()
        )
    }
}

enum class SystemType {
    SKIN, CARDIOVASCULAR, MUSCLE_JOINT, NERVOUS_SYSTEM, RESPIRATORY, URINARY, IMMUNE_METABOLIC, DIGESTIVE, INDIVIDUAL, WITHOUT_BALM
}

enum class ValveType {
    FIRST_BALM, SECOND_BALM, THIRD_BALM, FOURTH_BALM,
}