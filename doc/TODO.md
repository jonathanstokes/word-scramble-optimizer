# TODO List

- ~~Run the 3x2 test with accurate timing for a baseline.~~
- ~~Subset the Dictionary by a maximum word length to see 3x2 improvement.~~
- ~~Penalize duplicates when rating.~~
- ~~Make the board/rating output prettier.~~
- ~~Investigate why AABAAB rates higher than BAABAA.  Might need more thorough generator tests.~~
- ~~Allow 2-letter words in the test runs.~~
- ~~Convert the main loop to a chain of Stream operations.~~
- Commit current state to source control.
- Add curseword Dictionary and apply rating penalties for matches.
    - Could we have a tiered dictionary, where:
        ~~-some words are worth more because they're on a good list~~
        ~~-other words are worth some because they're on a medium list~~
        -some words are worth negative because they're on a curse word list
- Try a profiler.
- Statically analyze code for package dependency cycles.
