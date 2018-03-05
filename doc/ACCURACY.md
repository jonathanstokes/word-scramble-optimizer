

### 23feb2018 after adding duplicate penalties
    ***************************************************************************************************************************

    ********************************************************************
    AAS
    ABO
    
    [AAS (at 0,0 going EAST), SAA (at 2,0 going WEST), ABO (at 0,1 going EAST), OBA (at 2,1 going WEST)]
    Rating = 0.7600000000000002
    
    ********************************************************************
    AAH
    AAS
    
    [AAH (at 0,0 going EAST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.6800000000000002
    
    ********************************************************************
    AAA
    AAS
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.6720000000000002
    
    ********************************************************************
    AAH
    AAL
    
    [AAH (at 0,0 going EAST), AAL (at 0,1 going EAST)]
    Rating = 0.6000000000000001
    
    ********************************************************************
    AAA
    AAH
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), AAH (at 0,1 going EAST)]
    Rating = 0.5920000000000001
    
    ********************************************************************
    AAA
    ABA
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), ABA (at 0,1 going EAST), ABA (at 2,1 going WEST)]
    Rating = 0.5840000000000001
    
    ********************************************************************
    AAB
    AAS
    
    [BAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.4600000000000001
    
    ********************************************************************
    AAB
    AAH
    
    [BAA (at 2,0 going WEST), AAH (at 0,1 going EAST)]
    Rating = 0.38000000000000006
    
    ********************************************************************
    AAA
    AAB
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), BAA (at 2,1 going WEST)]
    Rating = 0.37200000000000005
    
    ********************************************************************
    AAS
    AAS
    
    [AAS (at 0,0 going EAST), SAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.342
    
    ***************************************************************************************************************************
    After processing 308915776 boards in 6.5 minutes (47,507,353.8 boards/minute), here are the top 10:
    ********************************************************************
    AAS
    ABO
    
    [AAS (at 0,0 going EAST), SAA (at 2,0 going WEST), ABO (at 0,1 going EAST), OBA (at 2,1 going WEST)]
    Rating = 0.7600000000000002
    
    ********************************************************************
    AAH
    AAS
    
    [AAH (at 0,0 going EAST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.6800000000000002
    
    ********************************************************************
    AAA
    AAS
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.6720000000000002
    
    ********************************************************************
    AAH
    AAL
    
    [AAH (at 0,0 going EAST), AAL (at 0,1 going EAST)]
    Rating = 0.6000000000000001
    
    ********************************************************************
    AAA
    AAH
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), AAH (at 0,1 going EAST)]
    Rating = 0.5920000000000001
    
    ********************************************************************
    AAA
    ABA
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), ABA (at 0,1 going EAST), ABA (at 2,1 going WEST)]
    Rating = 0.5840000000000001
    
    ********************************************************************
    AAB
    AAS
    
    [BAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.4600000000000001
    
    ********************************************************************
    AAB
    AAH
    
    [BAA (at 2,0 going WEST), AAH (at 0,1 going EAST)]
    Rating = 0.38000000000000006
    
    ********************************************************************
    AAA
    AAB
    
    [AAA (at 0,0 going EAST), AAA (at 2,0 going WEST), BAA (at 2,1 going WEST)]
    Rating = 0.37200000000000005
    
    ********************************************************************
    AAS
    AAS
    
    [AAS (at 0,0 going EAST), SAA (at 2,0 going WEST), AAS (at 0,1 going EAST), SAA (at 2,1 going WEST)]
    Rating = 0.342


### 24feb2018 after fixing a big Comparator problem that was eliminating like ratings.
(And after improving the ascii rendering)

    Generating 2.44140625E8 boards.
    Loading dictionary from ./src/main/resources/dwyl_alpha_word_list.txt.
    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    ***************************************************************************************************************************
    After processing 308915776 boards in 7.7 minutes (40,213,198 boards/minute), here are the top 10:
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) ZAG (at 0,1 going EAST)
    │ Z A G │   DOZ (at 2,0 going WEST) GAZ (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YUP (at 0,1 going EAST)
    │ Y U P │   DOZ (at 2,0 going WEST) PUY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YUG (at 0,1 going EAST)
    │ Y U G │   DOZ (at 2,0 going WEST) GUY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YOW (at 0,1 going EAST)
    │ Y O W │   DOZ (at 2,0 going WEST) WOY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YOT (at 0,1 going EAST)
    │ Y O T │   DOZ (at 2,0 going WEST) TOY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YOR (at 0,1 going EAST)
    │ Y O R │   DOZ (at 2,0 going WEST) ROY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YON (at 0,1 going EAST)
    │ Y O N │   DOZ (at 2,0 going WEST) NOY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YOM (at 0,1 going EAST)
    │ Y O M │   DOZ (at 2,0 going WEST) MOY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YOB (at 0,1 going EAST)
    │ Y O B │   DOZ (at 2,0 going WEST) BOY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YEW (at 0,1 going EAST)
    │ Y E W │   DOZ (at 2,0 going WEST) WEY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YES (at 0,1 going EAST)
    │ Y E S │   DOZ (at 2,0 going WEST) SEY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YEH (at 0,1 going EAST)
    │ Y E H │   DOZ (at 2,0 going WEST) HEY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YED (at 0,1 going EAST)
    │ Y E D │   DOZ (at 2,0 going WEST) DEY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAW (at 0,1 going EAST)
    │ Y A W │   DOZ (at 2,0 going WEST) WAY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAT (at 0,1 going EAST)
    │ Y A T │   DOZ (at 2,0 going WEST) TAY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAS (at 0,1 going EAST)
    │ Y A S │   DOZ (at 2,0 going WEST) SAY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAR (at 0,1 going EAST)
    │ Y A R │   DOZ (at 2,0 going WEST) RAY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAP (at 0,1 going EAST)
    │ Y A P │   DOZ (at 2,0 going WEST) PAY (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 0.76
    │ Z O D │   ZOD (at 0,0 going EAST) YAN (at 0,1 going EAST)
    │ Y A N │   DOZ (at 2,0 going WEST) NAY (at 2,1 going WEST)
    └───────┘   
Plus hundreds more ties for first with 0.76 ratings.    

## 25feb2018 after adding multiple word lists with point values for each
With short_normal_word_list.txt added.

    Generating 2.44140625E8 boards.
    Loading dictionary from ./src/main/resources/dwyl_alpha_word_list.txt.
    Loading dictionary from ./src/main/resources/john_lawler_word_list.txt.
    Loading dictionary from ./src/main/resources/short_normal_word_list.txt.
    Dictionary ready with 2738 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 308915776 boards in 2.9 minutes (105,781,602.2 boards/minute), here are the top 30:
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BAD (at 0,1 going EAST)
    │ B A D │   ERA (at 2,0 going WEST) DAB (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BAG (at 0,1 going EAST)
    │ B A G │   ERA (at 2,0 going WEST) GAB (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BAN (at 0,1 going EAST)
    │ B A N │   ERA (at 2,0 going WEST) NAB (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BAT (at 0,1 going EAST)
    │ B A T │   ERA (at 2,0 going WEST) TAB (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BID (at 0,1 going EAST)
    │ B I D │   ERA (at 2,0 going WEST) DIB (at 2,1 going WEST)
    └───────┘   
    
    ┌───────┐   Rating: 3.04
    │ A R E │   ARE (at 0,0 going EAST) BOG (at 0,1 going EAST)
    │ B O G │   ERA (at 2,0 going WEST) GOB (at 2,1 going WEST)
    └───────┘   

## 25feb2018 same thing but 2-letter words

    Generating 2.44140625E8 boards.
    Loading dictionary from ./src/main/resources/dwyl_alpha_word_list.txt.
    Loading dictionary from ./src/main/resources/john_lawler_word_list.txt.
    Loading dictionary from ./src/main/resources/short_normal_word_list.txt.
    Dictionary ready with 2738 words.  Longest word is 3 characters long.
    Rating boards, based on words 2 letters long or longer.
    ***************************************************************************************************************************
    After processing 308915776 boards in 20.9 minutes (14,782,840 boards/minute), here are the top 30:
    ┌───────┐   Rating: 3.582
    │ T I S │   TI (at 0,0 going EAST)      TO (at 0,0 going SOUTHEAST) IN (at 1,0 going SOUTHEAST) SN (at 2,0 going SOUTH)     SO (at 2,0 going SOUTHWEST) DI (at 0,1 going NORTHEAST) OS (at 1,1 going NORTHEAST) NO (at 2,1 going WEST)      NI (at 2,1 going NORTHWEST)
    │ D O N │   TIS (at 0,0 going EAST)     IS (at 1,0 going EAST)      IT (at 1,0 going WEST)      SI (at 2,0 going WEST)      DO (at 0,1 going EAST)      DT (at 0,1 going NORTH)     OD (at 1,1 going WEST)      NOD (at 2,1 going WEST)     
    └───────┘   TD (at 0,0 going SOUTH)     IO (at 1,0 going SOUTH)     ID (at 1,0 going SOUTHWEST) SIT (at 2,0 going WEST)     DON (at 0,1 going EAST)     ON (at 1,1 going EAST)      OT (at 1,1 going NORTHWEST) NS (at 2,1 going NORTH)     
    
    ┌───────┐   Rating: 3.5724
    │ T I N │   TI (at 0,0 going EAST)      TO (at 0,0 going SOUTHEAST) ID (at 1,0 going SOUTHEAST) NI (at 2,0 going WEST)      GO (at 0,1 going EAST)      GT (at 0,1 going NORTH)     OG (at 1,1 going WEST)      DOG (at 2,1 going WEST)
    │ G O D │   TIN (at 0,0 going EAST)     IN (at 1,0 going EAST)      IT (at 1,0 going WEST)      NIT (at 2,0 going WEST)     GOD (at 0,1 going EAST)     OD (at 1,1 going EAST)      OT (at 1,1 going NORTHWEST) DN (at 2,1 going NORTH)
    └───────┘   TG (at 0,0 going SOUTH)     IO (at 1,0 going SOUTH)     ND (at 2,0 going SOUTH)     NO (at 2,0 going SOUTHWEST) GI (at 0,1 going NORTHEAST) ON (at 1,1 going NORTHEAST) DO (at 2,1 going WEST)      DI (at 2,1 going NORTHWEST)
    
    ┌───────┐   Rating: 3.553
    │ T I S │   TI (at 0,0 going EAST)      TO (at 0,0 going SOUTHEAST) IN (at 1,0 going SOUTHEAST) SN (at 2,0 going SOUTH)     SO (at 2,0 going SOUTHWEST) WI (at 0,1 going NORTHEAST) OS (at 1,1 going NORTHEAST) NO (at 2,1 going WEST)      NI (at 2,1 going NORTHWEST)
    │ W O N │   TIS (at 0,0 going EAST)     IS (at 1,0 going EAST)      IT (at 1,0 going WEST)      SI (at 2,0 going WEST)      WO (at 0,1 going EAST)      WT (at 0,1 going NORTH)     OW (at 1,1 going WEST)      NOW (at 2,1 going WEST)     
    └───────┘   TW (at 0,0 going SOUTH)     IO (at 1,0 going SOUTH)     IW (at 1,0 going SOUTHWEST) SIT (at 2,0 going WEST)     WON (at 0,1 going EAST)     ON (at 1,1 going EAST)      OT (at 1,1 going NORTHWEST) NS (at 2,1 going NORTH)     

