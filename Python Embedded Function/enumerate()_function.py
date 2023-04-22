#enumerate(iterable, start)

lang = ["C++", "Python", "Java"]

enu = enumerate(iterable = lang, start = 1)

# print(list(enu))

for count, itera in enumerate(enu):
    print(itera)


