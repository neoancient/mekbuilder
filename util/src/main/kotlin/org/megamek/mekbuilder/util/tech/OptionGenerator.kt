package org.megamek.mekbuilder.util.tech

import com.fasterxml.jackson.databind.ObjectMapper
import org.megamek.mekbuilder.tech.*
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 *
 */
class OptionGenerator {
    val map = mapOf(
        ConstructionOptionKey.OMNI to constructionOption {
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XEED"
                clanProgression {
                    prototype = "~2854(CCY/CSF)"
                    production = "2856(CCY)"
                    common = "2864"
                }
                isProgression {
                    production = "3052(DC)"
                    common = "3052"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.OMNI_VEHICLE to constructionOption {
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XEED"
                clanProgression {
                    prototype = "~2854(CCY/CSF)"
                    production = "2856(CCY)"
                    common = "2864"
                }
                isProgression {
                    prototype = "~3008(MERC)"
                    production = "3052(DC)"
                    common = "3052"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.PATCHWORK_ARMOR to constructionOption {
            techProgression {
                techBase = TechBase.ALL
                availability = "EDEE"
                progression {
                    prototype = "1950"
                    production = "3075"
                    common = "~3080"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MIXED_TECH to constructionOption {
            techProgression {
                techBase = TechBase.ALL
                availability = "XXED"
                clanProgression {
                    prototype = "~2820(CLAN)"
                    production = "~3082"
                    common = "~3115"
                }
                isProgression {
                    prototype = "~3050(DC/FS/LC)"
                    production = "~3082"
                    common = "~3115"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.MEK_STANDARD to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.MEK_ULTRALIGHT
            nextWeightKey = ConstructionOptionKey.MEK_SUPERHEAVY
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CEDC"
                progression {
                    prototype = "2460(TH)"
                    production = "2470(TH)"
                    common = "2500"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.MEK_PRIMITIVE to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "DXFF"
                isProgression {
                    prototype = "2439(TH)"
                    production = "2443(TH)"
                    common = "2470"
                    extinction = "2520"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MEK_BIMODAL_LAM to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 55.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "EFXX"
                isProgression {
                    prototype = "2680(TH)"
                    production = "2684(TH)"
                    extinction = "2781"
                }
                clanProgression {
                    prototype = "2680(TH)"
                    production = "2684(TH)"
                    extinction = "2801"
                }
                staticLevel = TechLevel.EXPERIMENTAL
            }
        },
        ConstructionOptionKey.MEK_STANDARD_LAM to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 55.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "DEFF"
                isProgression {
                    prototype = "2683(TH)"
                    production = "2688(TH)"
                    extinction = "3085"
                }
                clanProgression {
                    prototype = "2683(TH)"
                    production = "2688(TH)"
                    extinction = "2825"
                }
                staticLevel = TechLevel.EXPERIMENTAL
            }
        },
        ConstructionOptionKey.MEK_QUADVEE to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.CLAN
                rating = Rating.RATING_F
                availability = "XXXF"
                clanProgression {
                    prototype = "~3130(CHH)"
                    production = "3135(CHH)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MEK_TRIPOD to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
            nextWeightKey = ConstructionOptionKey.MEK_SUPERHEAVY_TRIPOD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "FFFE"
                isProgression {
                    prototype = "~2585(TH)"
                    production = "2602(TH)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MEK_SUPERHEAVY to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.MEK_STANDARD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "XXFF"
                isProgression {
                    prototype = "3077(WB)"
                    production = "3078(WB)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MEK_SUPERHEAVY_TRIPOD to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.MEK_TRIPOD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "XXXF"
                isProgression {
                    prototype = "~3135(RS)"
                    production = "3136(RS)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.MEK_ULTRALIGHT to unitConstructionOption {
            unitType = UnitType.BATTLE_MEK
            minWeight = 10.0
            maxWeight = 15.0
            weightIncrement = 5.0
            nextWeightKey = ConstructionOptionKey.MEK_STANDARD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "XXXF"
                isProgression {
                    prototype = "~3135(RS)"
                    production = "3136(RS)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.IMEK_STANDARD to unitConstructionOption {
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
            nextWeightKey = ConstructionOptionKey.IMEK_SUPERHEAVY
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CCCB"
                progression {
                    prototype = "2460(TH)"
                    production = "2470(TH)"
                    common = "2500"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.IMEK_PRIMITIVE to unitConstructionOption {
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "CXFF"
                isProgression {
                    prototype = "2300(TA)"
                    production = "2350(TH)"
                    common = "2425"
                    extinction = "2520"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.IMEK_SUPERHEAVY to unitConstructionOption {
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.IMEK_STANDARD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "XXFF"
                isProgression {
                    prototype = "~2930(FW)"
                    production = "2940(FW)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.IMEK_TRIPOD to unitConstructionOption {
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
            nextWeightKey = ConstructionOptionKey.IMEK_SUPERHEAVY_TRIPOD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "FFFE"
                isProgression {
                    prototype = "~2585(TH)"
                    production = "2602(TH)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.IMEK_SUPERHEAVY_TRIPOD to unitConstructionOption {
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.IMEK_TRIPOD
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "XFXF"
                isProgression {
                    prototype = "~2930(FW)"
                    production = "2940(FW)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.PROTOMEK_STANDARD to unitConstructionOption {
            unitType = UnitType.PROTOMEK
            minWeight = 2.0
            maxWeight = 9.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.PROTOMEK_ULTRAHEAVY
            techProgression {
                techBase = TechBase.CLAN
                rating = Rating.RATING_F
                availability = "XXED"
                clanProgression {
                    prototype = "~3055(CSJ)"
                    production = "3059(CSJ)"
                    common = "3060"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.PROTOMEK_ULTRAHEAVY to unitConstructionOption {
            unitType = UnitType.PROTOMEK
            minWeight = 10.0
            maxWeight = 15.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.PROTOMEK_STANDARD
            techProgression {
                techBase = TechBase.CLAN
                rating = Rating.RATING_F
                availability = "XXDD"
                clanProgression {
                    prototype = "3075(CLAN)"
                    production = "~3083(CCY)"
                    common = "3100"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.PROTOMEK_QUAD to unitConstructionOption {
            unitType = UnitType.PROTOMEK
            minWeight = 2.0
            maxWeight = 15.0
            weightIncrement = 1.0
            techProgression {
                techBase = TechBase.CLAN
                rating = Rating.RATING_F
                availability = "XXED"
                clanProgression {
                    prototype = "3075(CLAN)"
                    production = "~3083(CCC)"
                    common = "3100"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.PROTOMEK_GLIDER to unitConstructionOption {
            unitType = UnitType.PROTOMEK
            minWeight = 2.0
            maxWeight = 15.0
            weightIncrement = 1.0
            techProgression {
                techBase = TechBase.CLAN
                rating = Rating.RATING_F
                availability = "XXED"
                clanProgression {
                    prototype = "3075(CLAN)"
                    production = "~3084(CSR)"
                    common = "3100"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.CV_WHEELED to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 80.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_WHEELED
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_TRACKED to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 100.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_TRACKED
            motiveType = MotiveType.TRACKED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_HOVER to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 50.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_HOVER
            motiveType = MotiveType.HOVER
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_VTOL to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 30.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_VTOL
            motiveType = MotiveType.VTOL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_WIGE to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 80.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_WIGE
            motiveType = MotiveType.WIGE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_NAVAL_DISPLACEMENT to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 300.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_DISPLACEMENT
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_NAVAL_HYDROFOIL to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 100.0
            weightIncrement = 1.0
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_NAVAL_SUBMARINE to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 300.0
            weightIncrement = 1.0
            nextWeightKey = ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_SUBMARINE
            motiveType = MotiveType.SUBMERSIBLE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CCCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_WHEELED to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 81.0
            maxWeight = 160.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_WHEELED
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_TRACKED to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 101.0
            maxWeight = 200.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_TRACKED
            motiveType = MotiveType.TRACKED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_HOVER to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 51.0
            maxWeight = 100.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_HOVER
            motiveType = MotiveType.HOVER
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_VTOL to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 31.0
            maxWeight = 60.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_VTOL
            motiveType = MotiveType.VTOL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_WIGE to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 81.0
            maxWeight = 160.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_WIGE
            motiveType = MotiveType.WIGE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_DISPLACEMENT to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 301.0
            maxWeight = 555.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_NAVAL_DISPLACEMENT
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_SUBMARINE to vehicleConstructionOption {
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 301.0
            maxWeight = 555.0
            weightIncrement = 1.0
            prevWeightKey = ConstructionOptionKey.CV_NAVAL_SUBMARINE
            motiveType = MotiveType.SUBMERSIBLE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "EFFE"
                progression {
                    prototype = "~2470(LC)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.SV_WHEELED_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_WHEELED_M
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "AAAA"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_WHEELED_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 80.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_WHEELED_S
            nextWeightKey = ConstructionOptionKey.SV_WHEELED_L
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "ABAA"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_WHEELED_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 80.5
            maxWeight = 160.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_WHEELED_M
            motiveType = MotiveType.WHEELED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "BCBB"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_TRACKED_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_TRACKED_M
            motiveType = MotiveType.TRACKED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "BCBB"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_TRACKED_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_TRACKED_S
            nextWeightKey = ConstructionOptionKey.SV_TRACKED_L
            motiveType = MotiveType.TRACKED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "BCBB"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_TRACKED_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 200.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_TRACKED_M
            motiveType = MotiveType.TRACKED
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_HOVER_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_HOVER_M
            motiveType = MotiveType.HOVER
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "ABAA"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_HOVER_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 50.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_HOVER_S
            nextWeightKey = ConstructionOptionKey.SV_HOVER_L
            motiveType = MotiveType.HOVER
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "ABAA"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_HOVER_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 50.5
            maxWeight = 100.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_HOVER_M
            motiveType = MotiveType.HOVER
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "BCBB"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_VTOL_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_VTOL_M
            motiveType = MotiveType.VTOL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_VTOL_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 30.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_VTOL_S
            nextWeightKey = ConstructionOptionKey.SV_VTOL_L
            motiveType = MotiveType.VTOL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_VTOL_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 30.5
            maxWeight = 60.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_VTOL_M
            motiveType = MotiveType.VTOL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "DEDD"
                progression {
                    prototype = PS
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_NAVAL_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_NAVAL_M
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_NAVAL_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_NAVAL_S
            nextWeightKey = ConstructionOptionKey.SV_NAVAL_L
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_NAVAL_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 100000.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_NAVAL_M
            motiveType = MotiveType.NAVAL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "CEDD"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_WIGE_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_WIGE_M
            motiveType = MotiveType.WIGE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "BCBB"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_WIGE_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 80.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_WIGE_S
            nextWeightKey = ConstructionOptionKey.SV_WIGE_L
            motiveType = MotiveType.WIGE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "BCBB"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_WIGE_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 80.5
            maxWeight = 160.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_WIGE_M
            motiveType = MotiveType.WIGE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CDCC"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_FIXED_WING_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_FIXED_WING_M
            motiveType = MotiveType.AERODYNE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_FIXED_WING_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_FIXED_WING_S
            nextWeightKey = ConstructionOptionKey.SV_FIXED_WING_L
            motiveType = MotiveType.AERODYNE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_FIXED_WING_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 200.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_FIXED_WING_M
            motiveType = MotiveType.AERODYNE
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "DEDD"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_AIRSHIP_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_AIRSHIP_M
            motiveType = MotiveType.AIRSHIP
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CDCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_AIRSHIP_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_AIRSHIP_S
            nextWeightKey = ConstructionOptionKey.SV_AIRSHIP_L
            motiveType = MotiveType.AIRSHIP
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_B
                availability = "DEDD"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SV_AIRSHIP_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 1000.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_AIRSHIP_M
            motiveType = MotiveType.AIRSHIP
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "DEDD"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_RAIL_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_RAIL_M
            motiveType = MotiveType.RAIL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CCCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_RAIL_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_RAIL_S
            nextWeightKey = ConstructionOptionKey.SV_RAIL_L
            motiveType = MotiveType.RAIL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CCCC"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_RAIL_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 600.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_RAIL_M
            motiveType = MotiveType.RAIL
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_A
                availability = "CDDD"
                progression {
                    prototype = PS
                    production = PS
                    common = PS
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_SATELLITE_S to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
            nextWeightKey = ConstructionOptionKey.SV_SATELLITE_M
            motiveType = MotiveType.STATION_KEEPING
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CDCC"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_SATELLITE_M to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_SATELLITE_S
            nextWeightKey = ConstructionOptionKey.SV_SATELLITE_L
            motiveType = MotiveType.STATION_KEEPING
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "CDDD"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SV_SATELLITE_L to vehicleConstructionOption {
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 300.0
            weightIncrement = 0.5
            prevWeightKey = ConstructionOptionKey.SV_SATELLITE_M
            motiveType = MotiveType.STATION_KEEPING
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "DEDD"
                progression {
                    prototype = ES
                    production = ES
                    common = ES
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        ConstructionOptionKey.BA_EXOSKELETON to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 80
            maxKg = 400
            kgIncrement = 1
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_C
                availability = "BBBB"
                progression {
                    prototype = "~2100"
                    common = "~2200"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.BA_PAL to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 80
            maxKg = 400
            kgIncrement = 1
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "FXED"
                nextWeightKey = ConstructionOptionKey.BA_LIGHT
                isProgression {
                    prototype = "2710(TH)"
                    common = "3058"
                    extinction = "2766"
                    reintroduction = "2905(CS)"
                }
                clanProgression {
                    prototype = "2710(TH)"
                    common = "3058"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.BA_LIGHT to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
            prevWeightKey = ConstructionOptionKey.BA_PAL
            nextWeightKey = ConstructionOptionKey.BA_MEDIUM
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XFED"
                clanProgression {
                    prototype = "~2865(CWF)"
                    production = "2870(CIH)"
                    common = "2900"
                }
                isProgression {
                    prototype = "3050(FS/LC)"
                    common = "3050"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.BA_MEDIUM to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
            prevWeightKey = ConstructionOptionKey.BA_LIGHT
            nextWeightKey = ConstructionOptionKey.BA_HEAVY
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XDDD"
                clanProgression {
                    prototype = "~2840(CGS)"
                    production = "2868(CWF)"
                    common = "2875"
                }
                isProgression {
                    prototype = "3052(FS/LC/CS)"
                    common = "3052"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.BA_HEAVY to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
            prevWeightKey = ConstructionOptionKey.BA_MEDIUM
            nextWeightKey = ConstructionOptionKey.BA_ASSAULT
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XFED"
                clanProgression {
                    prototype = "~2867(CWF)"
                    production = "2875(CHH)"
                    common = "3058"
                }
                isProgression {
                    prototype = "3050(FS/LC)"
                    common = "3050"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.BA_ASSAULT to unitConstructionOption {
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            weightIncrement = 5.0
            prevWeightKey = ConstructionOptionKey.BA_HEAVY
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XFED"
                clanProgression {
                    prototype = "~2870(CNC)"
                    production = "2877(CGB)"
                    common = "3060"
                }
                isProgression {
                    prototype = "3058(DC)"
                    common = "3060"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.ASF_PRIMITIVE to unitConstructionOption {
            unitType = UnitType.ASF
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "DXFF"
                progression {
                    prototype = ES
                    production = "~2200(TA)"
                    extinction = "2520"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.ASF_STANDARD to unitConstructionOption {
            unitType = UnitType.ASF
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CEDC"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.CF_STANDARD to unitConstructionOption {
            unitType = UnitType.CONV_FIGHTER
            minWeight = 5.0
            maxWeight = 50.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CDCB"
                progression {
                    production = "2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SC_STANDARD to unitConstructionOption {
            unitType = UnitType.SMALL_CRAFT
            minWeight = 100.0
            maxWeight = 200.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "DEDD"
                progression {
                    production = "2350(TH)"
                    common = "2400"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.SC_PRIMITIVE to unitConstructionOption {
            unitType = UnitType.SMALL_CRAFT
            minWeight = 100.0
            maxWeight = 200.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "DXFF"
                isProgression {
                    prototype = ES
                    production = "~2200(TA)"
                    extinction = "2400"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.DS_STANDARD_SPHEROID to unitConstructionOption {
            unitType = UnitType.DROPSHIP
            minWeight = 200.0
            maxWeight = 100000.0
            weightIncrement = 100.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "DEDD"
                progression {
                    production = "~2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.DS_STANDARD_AERODYNE to unitConstructionOption {
            unitType = UnitType.DROPSHIP
            minWeight = 200.0
            maxWeight = 35000.0
            weightIncrement = 100.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "DEDD"
                progression {
                    production = "~2470(TH)"
                    common = "2490"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.DS_PRIMITIVE_SPHEROID to unitConstructionOption {
            unitType = UnitType.DROPSHIP
            minWeight = 200.0
            maxWeight = 100000.0
            weightIncrement = 100.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "DXXX"
                progression {
                    prototype = ES
                    production = "~2200(TH)"
                    extinction = "2500"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.DS_PRIMITIVE_AERODYNE to unitConstructionOption {
            unitType = UnitType.SMALL_CRAFT
            minWeight = 200.0
            maxWeight = 35000.0
            weightIncrement = 100.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "DXXX"
                isProgression {
                    prototype = ES
                    production = "~2200(TH)"
                    extinction = "2500"
                }
                staticLevel = TechLevel.STANDARD
            }
        },
        ConstructionOptionKey.JS_STANDARD to unitConstructionOption {
            unitType = UnitType.JUMPSHIP
            minWeight = 50000.0
            maxWeight = 500000.0
            weightIncrement = 1000.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "DEDF"
                progression {
                    production = "~2300(TA)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.JS_PRIMITIVE to unitConstructionOption {
            unitType = UnitType.WARSHIP
            minWeight = 50000.0
            maxWeight = 1000000.0
            weightIncrement = 1000.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "DXXX"
                isProgression {
                    prototype = "~2100"
                    production = "~2200(TA)"
                    extinction = "2500"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.WARSHIP to unitConstructionOption {
            unitType = UnitType.WARSHIP
            minWeight = 100000.0
            maxWeight = 2500000.0
            weightIncrement = 10000.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "DEEF"
                isProgression {
                    prototype = "~2300(TA)"
                    production = "2305(TH)"
                    extinction = "2950"
                    reintroduction = "3050(FS/LC/DC)"
                }
                clanProgression {
                    prototype = "~2300(TA)"
                    production = "2305(TH)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.WARSHIP_SUBCOMPACT to unitConstructionOption {
            unitType = UnitType.WARSHIP
            minWeight = 5000.0
            maxWeight = 25000.0
            weightIncrement = 100.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "FXFF"
                isProgression {
                    prototype = "~2620(TH)"
                    extinction = "2850"
                }
                clanProgression {
                    prototype = "~2620(TH)"
                }
                staticLevel = TechLevel.EXPERIMENTAL
            }
        },
        ConstructionOptionKey.SPACE_STATION_STANDARD to unitConstructionOption {
            unitType = UnitType.SPACE_STATION
            minWeight = 2000.0
            maxWeight = 2500000.0
            weightIncrement = 500.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "CDCC"
                progression {
                    prototype = ES
                    extinction = ES
                }
                staticLevel = TechLevel.ADVANCED
            }
        },
        ConstructionOptionKey.SPACE_STATION_MODULAR to unitConstructionOption {
            unitType = UnitType.SPACE_STATION
            minWeight = 2000.0
            maxWeight = 2500000.0
            weightIncrement = 500.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "EXXE"
                progression {
                    prototype = "2565(TH)"
                    production = "2585(TH)"
                    extinction = "2790"
                    reintroduction = "3090(RS)"
                }
                staticLevel = TechLevel.ADVANCED
            }
        }
    )
}

fun main() {
    val generator = OptionGenerator()
    val mapper = ObjectMapper()
    val output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generator.map)
    val f = File("util/data", "construction_options.json")
    val os = FileOutputStream(f)
    val pw = PrintWriter(os)
    pw.println(output)
    pw.flush()
    pw.close()
    os.flush()
    os.close()
}