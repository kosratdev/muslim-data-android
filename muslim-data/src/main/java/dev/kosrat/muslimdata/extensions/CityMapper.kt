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
            val duhok = listOf("Sumel", "Zawita", "Atrish", "Sharya", "Mrebah")
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
                "Qushtapa",
                "Kasnazan"
            )

            val kirkuk = listOf("Taza Khurmatu")

            val sulaymaniyah = listOf(
                "Kalar",
                "Mawat",
                "Arbat",
            )
            val qaladiza = listOf(
                "Halsho",
                "Nawdasht",
                "Piramagroon",
                "Tasluja",
                "Bazian",
                "Sangasar",
                "Zharawa",
                "Sangaw",
            )
            val kifri = listOf("Dokan", "Takiya", "Qadir Karam", "Aghjalar")
            val chamchamal = listOf(
                "Ranya",
                "Hajiawa",
                "Sarkapkan",
                "Khalakan",
                "Taqtaq",
                "Hizop",
                "Chwarqurna",
                "Shorsh",
                "Takiay Jabari"
            )
            val darbandikhan = listOf(
                "Zalan",
                "Gapilon",
                "Chwarta",
                "Barzinjah",
                "Zarayan",
                "Halebjai Taza",
                "Qara Dagh",
                "Khanaqin",
            )
            val halabja = listOf("Garmk", "Penjwen", "Nalparez", "Said Sadiq", "Sirwan", "Pshta")
            val khurmal = listOf("Byara", "Tawella", "Gokhlan", "Balkha")

            return mutableMapOf(
                "Duhok" to duhok,
                "Akre" to akre,
                "Bardarash" to bardarash,
                "Erbil" to erbil,
                "Kirkuk" to kirkuk,
                "Sulaymaniyah" to sulaymaniyah,
                "Qaladiza" to qaladiza,
                "Kifri" to kifri,
                "Chamchamal" to chamchamal,
                "Darbandikhan" to darbandikhan,
                "Halabja" to halabja,
                "Khurmal" to khurmal,
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
                "Sulaibikhat",
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