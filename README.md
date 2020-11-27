# Flash-Schedule-backend
## CSV File format
C,{CoursePrefix},{CourseCode},{CourseTitle},{CreditType},{Prerequisites}
<p>
L,{enrlRestr},{SLN},{SectionID},{Days},{Times},{status},{enrlNum},{maxCapacity},{Grading},{OtherCode},{OtherInfo},{Credits},{CourseFee}
<p>
Q,{enrlRestr},{SLN},{SectionID},{Days},{Times},{status},{enrlNum},{maxCapacity},{Grading},{OtherCode},{OtherInfo}
<p>

// If something is the middle is empty it will be like {something},{nothing},{something} (Ex. 18,,%J)
<p>
// If something at the end is empty, we need to enter a space (Ex. 5,{a sapce here})
