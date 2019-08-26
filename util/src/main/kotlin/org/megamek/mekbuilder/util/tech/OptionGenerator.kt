package org.megamek.mekbuilder.util.tech

import com.fasterxml.jackson.databind.ObjectMapper
import org.megamek.mekbuilder.tech.*
import org.megamek.mekbuilder.unit.MotiveType
import org.megamek.mekbuilder.unit.UnitType
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 * Utility for generating construction option json file.
 */
class OptionGenerator {
    val list = listOf(
        constructionOption {
            techProgression {
                key = ConstructionOptionKey.OMNI
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
        constructionOption {
            key = ConstructionOptionKey.OMNI_VEHICLE
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
        constructionOption {
            key = ConstructionOptionKey.PATCHWORK_ARMOR
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
        constructionOption {
            key = ConstructionOptionKey.MIXED_TECH
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

        unitConstructionOption {
            key = ConstructionOptionKey.MEK_STANDARD
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_PRIMITIVE
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_BIMODAL_LAM
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_STANDARD_LAM
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.MEK_QUADVEE_TRACKED
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
            motiveType = MotiveType.TRACKED
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.MEK_QUADVEE_WHEELED
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
            motiveType = MotiveType.WHEELED
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_TRIPOD
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 100.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_SUPERHEAVY
            unitType = UnitType.BATTLE_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_SUPERHEAVY_TRIPOD
            unitType = UnitType.BATTLE_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_ULTRALIGHT
            unitType = UnitType.BATTLE_MEK
            minWeight = 10.0
            maxWeight = 15.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.IS
                rating = Rating.RATING_D
                availability = "EFEE"
                progression {
                    prototype = "~2500(TH/FW)"
                    production = "2519(FW)"
                    common = "~3075"
                }
                staticLevel = TechLevel.ADVANCED
            }
        },

        unitConstructionOption {
            key = ConstructionOptionKey.IMEK_STANDARD
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.IMEK_PRIMITIVE
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
        unitConstructionOption {
            key = ConstructionOptionKey.IMEK_SUPERHEAVY
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.IMEK_TRIPOD
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 10.0
            maxWeight = 100.0
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.IMEK_SUPERHEAVY_TRIPOD
            unitType = UnitType.INDUSTRIAL_MEK
            minWeight = 105.0
            maxWeight = 200.0
            weightIncrement = 5.0
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

        unitConstructionOption {
            key = ConstructionOptionKey.PROTOMEK_STANDARD
            unitType = UnitType.PROTOMEK
            minWeight = 2.0
            maxWeight = 9.0
            weightIncrement = 1.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.PROTOMEK_ULTRAHEAVY
            unitType = UnitType.PROTOMEK
            minWeight = 10.0
            maxWeight = 15.0
            weightIncrement = 1.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.PROTOMEK_QUAD
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
        unitConstructionOption {
            key = ConstructionOptionKey.PROTOMEK_GLIDER
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

        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_WHEELED
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 80.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_TRACKED
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 100.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_HOVER
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 50.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_VTOL
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 30.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_WIGE
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 80.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_NAVAL_DISPLACEMENT
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 300.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_NAVAL_HYDROFOIL
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_NAVAL_SUBMARINE
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 1.0
            maxWeight = 300.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_WHEELED
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 81.0
            maxWeight = 160.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_TRACKED
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 101.0
            maxWeight = 200.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_HOVER
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 51.0
            maxWeight = 100.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_VTOL
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 31.0
            maxWeight = 60.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_WIGE
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 81.0
            maxWeight = 160.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_DISPLACEMENT
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 301.0
            maxWeight = 555.0
            weightIncrement = 1.0
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.CV_SUPERHEAVY_NAVAL_SUBMARINE
            unitType = UnitType.COMBAT_VEHICLE
            minWeight = 301.0
            maxWeight = 555.0
            weightIncrement = 1.0
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

        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WHEELED_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WHEELED_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 80.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WHEELED_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 80.5
            maxWeight = 160.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_TRACKED_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_TRACKED_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_TRACKED_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 200.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_HOVER_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_HOVER_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 50.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_HOVER_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 50.5
            maxWeight = 100.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_VTOL_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_VTOL_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 30.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_VTOL_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 30.5
            maxWeight = 60.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_NAVAL_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_NAVAL_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_NAVAL_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 100000.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WIGE_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WIGE_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 80.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_WIGE_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 80.5
            maxWeight = 160.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_FIXED_WING_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_FIXED_WING_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_FIXED_WING_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 200.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_AIRSHIP_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_AIRSHIP_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_AIRSHIP_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 1000.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_RAIL_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_RAIL_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 300.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_RAIL_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 300.5
            maxWeight = 600.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_SATELLITE_S
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 0.1
            maxWeight = 4.999
            weightIncrement = 0.001
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_SATELLITE_M
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 5.0
            maxWeight = 100.0
            weightIncrement = 0.5
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
        vehicleConstructionOption {
            key = ConstructionOptionKey.SV_SATELLITE_L
            unitType = UnitType.SUPPORT_VEHICLE
            minWeight = 100.5
            maxWeight = 300.0
            weightIncrement = 0.5
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

        unitConstructionOption {
            key = ConstructionOptionKey.BA_EXOSKELETON
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
        unitConstructionOption {
            key = ConstructionOptionKey.BA_PAL
            unitType = UnitType.BATTLE_ARMOR
            minKg = 80
            maxKg = 400
            kgIncrement = 1
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_D
                availability = "FXED"
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
        unitConstructionOption {
            key = ConstructionOptionKey.BA_LIGHT
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
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
        unitConstructionOption {
            key = ConstructionOptionKey.BA_MEDIUM
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
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
        unitConstructionOption {
            key = ConstructionOptionKey.BA_HEAVY
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            kgIncrement = 1
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
        unitConstructionOption {
            key = ConstructionOptionKey.BA_ASSAULT
            unitType = UnitType.BATTLE_ARMOR
            minKg = 401
            maxKg = 750
            weightIncrement = 5.0
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
        unitConstructionOption {
            key = ConstructionOptionKey.ASF_PRIMITIVE
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
        unitConstructionOption {
            key = ConstructionOptionKey.ASF_STANDARD
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
        unitConstructionOption {
            key = ConstructionOptionKey.CF_STANDARD
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
        unitConstructionOption {
            key = ConstructionOptionKey.SC_STANDARD
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
        unitConstructionOption {
            key = ConstructionOptionKey.SC_PRIMITIVE
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
        unitConstructionOption {
            key = ConstructionOptionKey.DS_STANDARD_SPHEROID
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
        unitConstructionOption {
            key = ConstructionOptionKey.DS_STANDARD_AERODYNE
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
        unitConstructionOption {
            key = ConstructionOptionKey.DS_PRIMITIVE_SPHEROID
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
        unitConstructionOption {
            key = ConstructionOptionKey.DS_PRIMITIVE_AERODYNE
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
        unitConstructionOption {
            key = ConstructionOptionKey.JS_STANDARD
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
        unitConstructionOption {
            key = ConstructionOptionKey.JS_PRIMITIVE
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
        unitConstructionOption {
            key = ConstructionOptionKey.WARSHIP
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
        unitConstructionOption {
            key = ConstructionOptionKey.WARSHIP_SUBCOMPACT
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
        unitConstructionOption {
            key = ConstructionOptionKey.SPACE_STATION_STANDARD
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
        unitConstructionOption {
            key = ConstructionOptionKey.SPACE_STATION_MODULAR
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
        },

    // Unofficial options from XTRO Boondoggles
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_QUAD_LAM_UNOFFICIAL
            unitType = UnitType.BATTLE_MEK
            minWeight = 20.0
            maxWeight = 55.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XXXX"
                isProgression {
                    prototype = "2690"
                }
                staticLevel = TechLevel.UNOFFICIAL
            }
        },
        unitConstructionOption {
            key = ConstructionOptionKey.MEK_HEAVY_LAM_UNOFFICIAL
            unitType = UnitType.BATTLE_MEK
            minWeight = 60.0
            maxWeight = 100.0
            weightIncrement = 5.0
            techProgression {
                techBase = TechBase.ALL
                rating = Rating.RATING_E
                availability = "XXXX"
                isProgression {
                    prototype = "2699"
                }
                staticLevel = TechLevel.UNOFFICIAL
            }
        }

    )
}

fun main() {
    val generator = OptionGenerator()
    val mapper = ObjectMapper()
    val output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generator.list)
    val f = File("util/data", "construction_options.json")
    val os = FileOutputStream(f)
    val pw = PrintWriter(os)
    pw.println(output)
    pw.flush()
    pw.close()
    os.flush()
    os.close()
}