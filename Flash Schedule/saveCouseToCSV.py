from selenium import webdriver
import re

dr=webdriver.Firefox(executable_path=r'D:\软件\geckodriver\geckodriver.exe') #replace wih your driver path
dr.maximize_window()
#url = input("Please enter time schedule url:")
url = "https://www.washington.edu/students/timeschd/WIN2021/math.html" # change url here
dr.get(url)

courseRegex = "^([A-Z&]+ *[A-Z&]) *([0-9]+) *(.*)"
courseTemplate = "C,{},{},{},{},{} \n"
lectureTemplate = "L,{},{},{},{},{},{},{},{},{},{},{},{},{} \n"
quizTemplate = "Q,{},{},{},{},{},{},{},{},{},{},{} \n"

dr.implicitly_wait(10)

f=open("courseList.csv","a")

tableList = dr.execute_script('return document.querySelectorAll("table");')
for i in range(3, len(tableList)):
    #print(tableList[i].get_attribute("bgcolor"))
    if (tableList[i].get_attribute("bgcolor") == "#99ccff"): #course record
        #print(tableList[i].text)
        matcher = re.search(courseRegex, tableList[i].text)
        prefix = matcher.group(1)
        code = matcher.group(2)
        rest = matcher.group(3)
        preReq = ""
        title = rest.split("Prerequisites")[0].strip()
        credit = ""
        if (re.search(r"\(.*?\)",title) != None):
            credit = re.search(r"[^()]+",re.search(r"\(.*?\)",title).group()).group()
        title = title.split("(")[0].strip()
        if ("Prerequisites" in rest):
            preReq = "Prerequisites"
        f.write(courseTemplate.format(prefix, code, title, credit, preReq))
        print(courseTemplate.format(prefix, code, title, credit, preReq))
    else:
        isQuiz = dr.execute_script(r'return arguments[0].textContent.split(/\s+/).indexOf("QZ") != -1;', tableList[i])
        enrlRestr = dr.execute_script(r'return arguments[0].querySelector("pre").firstChild.nodeValue;', tableList[i]).strip()
        numSLN = dr.execute_script(r'return arguments[0].querySelector("pre").firstElementChild.text;', tableList[i]).strip()
        sectionID = dr.execute_script(r'return arguments[0].querySelector("pre").firstElementChild.nextSibling.nodeValue.split("\n")[0].split(/\s+/)[1];', tableList[i])
        days = ""
        times = ""
        if (dr.execute_script(r'return arguments[0].querySelector("pre").firstElementChild.nextSibling.nodeValue.split("\n")[0].match(/to be arranged/) != null;', tableList[i])): # to be arranged
            days = "to be arranged"
            times = "to be arranged"
        else: # has meeting times
            days = dr.execute_script(r'return arguments[0].querySelector("pre").firstElementChild.nextSibling.nodeValue.split("\n")[0].match(/(?: )([MFWTTh]+)/)[1];', tableList[i])
            times = dr.execute_script(r'return arguments[0].querySelector("pre").firstElementChild.nextSibling.nodeValue.split("\n")[0].match(/[0-9]{3,}-[0-9]{3,}P?/)[0];', tableList[i])
        status = "Open"
        if (tableList[i].get_attribute("bgcolor") == "#d3d3d3"):
            status = "Closed"
        enrlNum = dr.execute_script(r'return arguments[0].textContent.split("\n")[0].match(/[0-9]+\/ *[0-9]+/)[0].split(/\//)[0].trim();', tableList[i])
        enrlLimit = dr.execute_script(r'return arguments[0].textContent.split("\n")[0].match(/[0-9]+\/ *[0-9]+/)[0].split(/\//)[1].trim();', tableList[i])
        grading = ""
        if (dr.execute_script(r'return arguments[0].textContent.split("\n")[0].match(/CR\/NC/) != null;', tableList[i])):
            grading = "CR/NC"
        otherCode = ""
        if (dr.execute_script(r'return arguments[0].textContent.split("\n")[0].trim().match(/(?: )[BDEHJORSW%#]+$/) != null;', tableList[i])):
            otherCode = dr.execute_script(r'return arguments[0].textContent.split("\n")[0].trim().match(/(?: )([BDEHJORSW%#]+)$/)[1];', tableList[i])
        otherInfo = dr.execute_script(r'let l = arguments[0].querySelector("pre").lastChild.nodeValue.split("\n");let s = "";let i = 1;for(i = 1; i < l.length; i++) { s = s + l[i].trim().replaceAll(","," ") + " ";}return s;', tableList[i])
        if (isQuiz): #quiz record
            f.write(quizTemplate.format(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, enrlLimit, grading, otherCode, otherInfo))
            print(quizTemplate.format(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, enrlLimit, grading, otherCode, otherInfo))
        else: #lecture record
            credit = dr.execute_script(r'var reg = /[0-9]+-*[0-9]*/g; let arr = arguments[0].textContent.split("\n")[0].matchAll(reg); arr.next(); return arr.next().value[0];', tableList[i])
            courseFee = ""
            if (dr.execute_script(r'return arguments[0].textContent.split("\n")[0].match(/(\$)([0-9]+)/) != null;', tableList[i])):
                courseFee = dr.execute_script(r'return arguments[0].textContent.split("\n")[0].match(/(\$)([0-9]+)/)[2];', tableList[i])
            f.write(lectureTemplate.format(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, enrlLimit, grading, otherCode, otherInfo, credit, courseFee))
            print(lectureTemplate.format(enrlRestr, numSLN, sectionID, days, times, status, enrlNum, enrlLimit, grading, otherCode, otherInfo, credit, courseFee))

f.close()
print("done")
