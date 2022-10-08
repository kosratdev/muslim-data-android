

import os


def toTime(textTime, is24=False):
    textTime = [int(numeric_string) for numeric_string in textTime.split(":")]
    if (is24):
        textTime[0] += 12
    return f"{int(textTime[0]):02d}:{int(textTime[1]):02d}"


input_path = "/Users/kosrat/Downloads/db"
output_path = "/Users/kosrat/Downloads/db-query"

files = os.listdir(input_path)
for file in files:
    f = open(input_path+"/"+file, "r")
    lines = [x.replace("\n", "") for x in f]
    city = file.replace(".txt", "")
    print(city)

    query = """
INSERT INTO prayer_time (country_code, city, date, fajr, sunrise, dhuhr, asr, maghrib, isha)
VALUES
"""

    for line in lines:
        cols = line.split("|")
        date = f"{int(cols[0]):02d}-{int(cols[1]):02d}"
        query += f"(\"IQ\", \"{city}\", \"{date}\", \"{toTime(cols[2])}\", \"{toTime(cols[3])}\", \"{toTime(cols[4])}\", \"{toTime(cols[5], True)}\", \"{toTime(cols[6], True)}\", \"{toTime(cols[7], True)}\"), \n"

    f = open(output_path+"/"+file, "a")
    f.write(query)
    f.close()
