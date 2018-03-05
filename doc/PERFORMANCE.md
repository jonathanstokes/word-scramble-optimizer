# Informal Performance Results

- Baseline - Macbook 22feb2018
  - `Dictionary ready with 370099 words.  Longest word is 31 characters long.`
  - `After processing 308915776 boards in 8.5 minutes (36,233,267 boards/minute), here...`
  - Results don't look good.  BAA is in the top 10, but only backwards (WEST).
- Shortened Dictionary - Macbook 22feb2018
  - `Dictionary ready with 2583 words.  Longest word is 3 characters long.`
  - `After processing 308915776 boards in 6.7 minutes (46,222,388.2 boards/minute), here...`
  - BAA is still wrong.
- After fixing the bad Comparator problem - Macbook 24feb2018


    Loading dictionary from ./src/main/resources/dwyl_alpha_word_list.txt.
    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards.
    ***************************************************************************************************************************
    After processing 308915776 boards in 7.7 minutes (40,213,198 boards/minute), here...

- After creating TopRatedBoards and simplifying the Main loop.

Something went badly wrong here, likely with TopRatedBoards accumulation.


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 47.2 minutes (2,119,666.5 boards/minute)

- Confirmed, after rolling back to pre-TopRatedBoards:


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards.
    ***************************************************************************************************************************
    After processing 100000000 boards in 2.7 minutes (36,585,812 boards/minute)
    After processing 200000000 boards in 4.9 minutes (40,555,611.9 boards/minute)
    After processing 308915776 boards in 7.3 minutes (42,076,299.5 boards/minute)

- After fixing TopRatedBoards to use a ConcurrentHashMap:


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 2.3 minutes (43,622,087.3 boards/minute), here are the top 10:
    After processing 200000000 boards in 4.3 minutes (46,722,058.3 boards/minute), here are the top 10:
    
    
    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 2.6 minutes (37,863,489.5 boards/minute), here are the top 10:
    After processing 200000000 boards in 4.8 minutes (42,006,069.9 boards/minute), here are the top 10:
    After processing 300000000 boards in 6.7 minutes (44,692,737.4 boards/minute), here are the top 10:
    After processing 308915776 boards in 6.9 minutes (44,901,527.8 boards/minute), here are the top 30:

- After changing the dictionary to allow 2-letter words, and 


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 2 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 22.2 minutes (4,511,858.3 boards/minute)
    After processing 200000000 boards in 43.7 minutes (4,579,703.4 boards/minute)
    After processing 300000000 boards in 65.2 minutes (4,604,209.4 boards/minute)
    After processing 308915776 boards in 66.7 minutes (4,630,995.1 boards/minute)
    

- Changing to Streams (back to 3-letter words), without parallel.


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 2.4 minutes (42,048,313.5 boards/minute), here are the top 10:
    After processing 200000000 boards in 4.8 minutes (41,762,226.8 boards/minute), here are the top 10:
    After processing 300000000 boards in 7.2 minutes (41,828,947.5 boards/minute), here are the top 10:
    After processing 308915776 boards in 7.3 minutes (42,036,887.7 boards/minute), here are the top 30:
    
- Streams, with parallel, without a Collector.


    Dictionary ready with 2583 words.  Longest word is 3 characters long.
    Rating boards, based on words 3 letters long or longer.
    ***************************************************************************************************************************
    After processing 100000000 boards in 1 minutes (103,560,764.3 boards/minute), here are the top 10:
    After processing 200000000 boards in 2 minutes (99,596,633.6 boards/minute), here are the top 10:
    After processing 300000000 boards in 3.1 minutes (95,477,546.9 boards/minute), here are the top 10:
    processedBoardCount=308915776, count()=308915776.
    ***************************************************************************************************************************
    After processing 308915776 boards in 3.2 minutes (95,653,379 boards/minute), here are the top 30:


