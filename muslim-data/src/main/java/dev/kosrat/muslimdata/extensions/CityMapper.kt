package dev.kosrat.muslimdata.extensions

class CityMapper {
    companion object {
        fun map(city: String, countryCode: String): String {
            val countries: MutableMap<String, MutableMap<String, List<String>>> = HashMap()
            countries["IQ"] = getIQCities()
            countries["IR"] = getIRCities()
            countries["KW"] = getKWCities()

            val cities = countries[countryCode]
            cities?.forEach {
                if (it.value.contains(city)) {
                    return it.key
                }
            }
            return city
        }

        private fun getIQCities(): MutableMap<String, List<String>> {
            val akre = listOf(
                "Amedi",
                "Sulav",
                "Kani",
                "Sheladiz",
                "Barzan",
                "Bele",
                "Shanidar",
                "Bujal",
                "Mergin",
                "Susna",
                "Sersink"
            )
            val bardarash = listOf("Mamuzin")
            val darbandikhan = listOf("Zarayan")
            val duhok = listOf("Sumel", "Zawita", "Atrish", "Sharya", "Mrebah")
            val erbil = listOf(
                "Kalak",
                "Pirmam",
                "Shaqlawa",
                "Harir",
                "Khalifan",
                "Rawanduz",
                "Soran",
                "Mergasur",
                "Galala",
                "Choman",
                "Hiran",
                "Makhmur",
                "Qushtapa",
                "Kasnazan"
            )
            val halabja = listOf("Khurmal", "Sirwan", "Byara", "Tawella")
            val kifri = listOf("Hajiawa", "Chwarqurna", "Ranya")
            val kirkuk = listOf("Taza Khurmatu")
            val koysinjaq = listOf("Taqtaq", "Khalakan")
            val qalatDizah = listOf("Sangasar", "Zharawa")
            val sulaymaniyah = listOf(
                "Dokan",
                "Bazian",
                "Chamchamal",
                "Qaran Dagh",
                "Arbat",
                "Penjwen",
                "Said Sadiq",
                "Kalar",
                "Takiya",
                "Shorsh"
            )
            return mutableMapOf(
                "Akre" to akre,
                "Bardarash" to bardarash,
                "Darbandikhan" to darbandikhan,
                "Duhok" to duhok,
                "Erbil" to erbil,
                "Halabja" to halabja,
                "Kifri" to kifri,
                "Kirkuk" to kirkuk,
                "Koysinjaq" to koysinjaq,
                "Qalat itoah" to qalatDizah,
                "Sulaymaniyah" to sulaymaniyah
            )
        }

        private fun getIRCities(): MutableMap<String, List<String>> {
            val sanandaj = listOf("Kamyaran", "Divandarreh", "Dehgolan", "Qorveh")
            val urmia = listOf("Orumiyeh")
            return mutableMapOf(
                "Sanandaj" to sanandaj,
                "Urmia" to urmia
            )
        }

        private fun getKWCities(): MutableMap<String, List<String>> {
            val alAsimah = listOf(
                "Kuwait City",
                "Dasman Palace",
                "Sharq",
                "Mirqab",
                "Jibla",
                "Dasma",
                "Daiya",
                "Salhia",
                "Bneid Al Qar",
                "Kaifan",
                "Mansouriya",
                "Abdullah al-Salem",
                "Nuzha",
                "Faiha",
                "Shamiya",
                "Rawda",
                "Adailiya",
                "Khaldiya",
                "Qadsiya",
                "Qortuba",
                "Surra",
                "Yarmouk",
                "Shuwaikh Industrial",
                "Rai",
                "Granada",
                "Sulaibikhat",
                "Doha",
                "Nahdha",
                "Jaber Al Ahmad",
                "Qairawan",
                "Ahmadi",
                "Al Wafrah",
                "Sabah Al Salem",
                "Messila",
                "Al-Masayel",
                "Adan",
                "Fnaitees",
                "Qusor",
                "Qurain",
                "Abu Fatira",
                "Mubarak Al Kabeer",
                "Jeleeb Al-Shuyoukh",
                "Eqaila",
                "Fintas",
                "Dahar",
                "Mahboula",
                "Hadiya",
                "Al-Riqqa",
                "Abu Halifa",
                "Fahad Al Ahmad",
                "Assabahiyah",
                "Mangaf",
                "Fahaheel",
                "South Sabahiya",
                "Ali Sabah Al Salem",
                "Shalayhat Mina Abdullah",
                "Zour",
                "Al Khiran"
            )
            val failaka = listOf("Zoor", "Kubbar Island", "Al-Nuwaiseeb")
            val abdali = listOf("Jahra")
            return mutableMapOf(
                "Al Asimah" to alAsimah,
                "Failaka Island" to failaka,
                "Abdali" to abdali
            )
        }
    }
}