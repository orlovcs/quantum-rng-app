# Quantum Random Number Generator
This is an Android app which allows for generation of almost completely random numbers. Access it ![here](https://play.google.com/store/apps/details?id=com.crimsonlabs.orlovcs.reaction).

# Play Store Description:
Tools using quantum truly randomized numbers include:
--- Lottery game picks including LottoMax, Euro Millions, Lotto 6/49, etc.
--- Password generation using letters, number and digits
--- Polyhedral dice rolls ranging from D3 to D1000
--- Number generation with min/max and decimal options

Options to turn off the API and fallback to local number generation as well as to copy and share the data.

Numbers are generated every time based on truly random data received from the ETH Zürich Quantum OpenQu or the Australian National University Quantum Random Numbers Server.

According to the many worlds interpretation of quantum physics, every time a wave function collapses(quantum number is generated in this case) every possible outcome is observed, but from different parallel universe created at the moment of the measurement.

In layman's terms, when you use this app to make a particular decision you end up enacting all possible outcomes, just in different universes. Somewhere out there is the version of you who took the road not taken by you.

Good Luck!


# todo
- change titlebar fonts
- double check all copy and share works
- App is not indexable by Google Search; consider adding at least one Activity with an ACTION-VIEW intent-filler
- randomized password slider menu option
- coin flip history and total counter

# done
- add manual random gen fall back make toasts to notofy if true random generated or manual fallback
- give option to turn off the api
- add multiple dice rolls, maybe 3
- How many coins?
- give # heads, #of tails
- standarize generate button location, font and size, make it above the toast location
- password missing manual generation toast
- about link works as a seperate textview: text25
- fix about page constraints
- fix the output constraints and standardize them
- password timeout is more than ten seconds for some reason
- rng constraint
- app icon and label
- selectable text
