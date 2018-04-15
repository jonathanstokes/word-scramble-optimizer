# TODO List

- ~~Run the 3x2 test with accurate timing for a baseline.~~
- ~~Subset the Dictionary by a maximum word length to see 3x2 improvement.~~
- ~~Penalize duplicates when rating.~~
- ~~Make the board/rating output prettier.~~
- ~~Investigate why AABAAB rates higher than BAABAA.  Might need more thorough generator tests.~~
- ~~Allow 2-letter words in the test runs.~~
- ~~Convert the main loop to a chain of Stream operations.~~
- ~~Commit current state to source control.~~
- ~~Add curseword Dictionary and apply rating penalties for matches.~~
    - ~~Could we have a tiered dictionary, where:~~
        - ~~some words are worth more because they're on a good list~~
        - ~~other words are worth some because they're on a medium list~~
        - ~~some words are worth negative because they're on a curse word list~~
- Run the 3x3 on a PC for a while to see what good results bubble up in the early few.
- Speed things up with pre-qualifying:
  - Create a PrequalifiedBoard that contains a Board and a BoardHistogram, or otherwise figure out
    how to use the histogram to short-circuit the analysis.  At first, we'll just need to gather
    data about good and bad boards.
  - Determine an acceptable vowel-consonant mix.
  - Can rating be short circuited when nothing is found on a row?  
- **Persistence**: Come up with persistence, of:
  - top rated boards.
  - analyzed boards (ranges, or individuals)
- Try a profiler.
  - Will dictionary looking be any faster if we eliminate too-short words?
- Add a README.md file.
- Statically analyze code for package dependency cycles.
- Non-brute force approaches:
  - From a good Board, can we radiate candidates that might be better, and follow the most
    promising veins? 
