# CashlessSocietyProtocol

## Introduction
### Purpose (วัตถุประสงค์)	
 	Cashless Society Protocol (CSP) เป็น application-level protocol สร้างขึ้นเพื่อใช้ในการส่งข้อมูลทางการเงิน ภายในระบบการใช้จ่ายโดยไม่ผ่านเงินสด ตามแนวคิดสังคมไร้เงินสด (Cashless Society)
 	โดย CSP นั้นถูกออกแบบมาให้ส่งเฉพาะข้อมูลในส่วนของธุรกรรมทางการเงิน โดยไม่ได้ครอบคลุมถึงระบบระบุตัวตน (เช่น ระบบ login), ระบบการจัดการฐานข้อมูล (เช่น การเรียกข้อมูลราคาสินค้าจาก server) หากต้องการทำระบบดังกล่าวควรใช้ร่วมกับ protocol อื่น เช่น HTTP
### Terminology (นิยามศัพท์)
#### Message
 	ข้อมูลที่รับ/ส่งภายในระบบ โดยใช้ไวยกรณ์ของ CSP protocol
#### Client
 	Program ผู้ส่ง message เพื่อขอดำเนินธุรกรรม
#### Server
 	Program ผู้รับ message เพื่อดำเนินธุรกรรมตาม message ที่ได้รับ และตอบกลับผลลัพธ์จากการทำธุรกรรมนั้น
#### Request
 	Message ที่ส่งโดย client เพื่อขอดำเนินธุรกรรม
#### Response
 	Message ที่ส่งโดย server เพื่อตอบผลลัพธ์จากธุรกรรม
#### Transaction (ธุรกรรม)
 	กิจกรรมทางการเงิน
### Overall Operation (ภาพรวมการทำงาน)	
 	CSP protocol ทำงานโดยการที่ client จะส่ง request message ไปยัง server จากนั้น server จะประมวลผล message ที่ส่งไป เพื่อดำเนินธุรกรรม และตอบกลับผลของธุรกรรมนั้นกลับไปยัง client ด้วย response message โดยใน 1 connection จะมีเพียง 1 request และ 1 response เท่านั้น	
 	โดยทั่วไป CSP จะทำงานบน TCP/IP connection และ default port คือ TCP 981
 
## Notational Conventions and Generic Grammar  
### Augmented BNF
 	คำอธิบายภายในเอกสารนี้ จะอธิบายด้วยบทบรรยายและ Augmented BNF ตามที่ระบุใน RFC 5234 โดยโครงสร้างของ augmented BNF มีดังนี้
#### Rule Naming
 	การตั้งชื่อ rule จะต้องขึ้นต้นด้วยตัวอักษรและตามด้วยลำดับของอักษร, ตัวเลข หรือเครื่องหมายยัติภังค์ (-) และ rule name เป็น case-insensitive
#### Rule Form
name = element crlf
 	ใช้สำหรับการนิยาม rule โดยที่ <name> คือชื่อของ rule, <elements> คือ rule หรือ terminal และ <crlf> คือ carriage return ตามด้วย line feed
#### Terminal Values
name = % base code
 	ใช้สำหรับนิยาม terminal โดยที่ <name> คือชื่อของ terminal. <base> คือฐานของเลข code และ <code> คือตัวเลขแทนตัวอักษรใน ASCII
#### Terminal Concatenation
 % base code1.code2
 	ใช้สำหรับการรวม code เช่น %d13 %d10 สามารถแทนด้วย %13.10
#### Concatenation
element1 element2
 	ใช้สำหรับการรวม element
#### Literal
"text"
 	ใช้สำหรับการระบุ string แบบ case-insensitive
#### Alternative
element1 / element2
 	ใช้สำหรับสร้างทางเลือก เช่น foo / bar หมายถึง <foo> หรือ <bar>
#### Sequence Group
(element1 element2)
 	ใช้สำหรับจัดกลุ่มของ element
#### Variable Repetition
<a>*<b>element
 	ใช้สำหรับการซ้ำ element อย่างน้อย a จนถึงอย่างมาก b เช่น 1*2<element> โดยค่าพื้นฐานของ a=0 และ b=infinity
#### Specific Repetition
<n>element
 	ใช้สำหรับการซ้ำ element จำนวน n
#### Optional Sequence
[element]
 	ใช้สำหรับระบุว่า element นี้เป็นตัวเลือก สามารถไม่ใส่ได้
#### Comment
; comment
 	ใช้สำหรับเขียน comment
### Core Rule
 	rule ต่อไปนี้เป็น rule ที่จะใช้ตลอดเอกสารนี้เพื่อใช้ในการอธิบายโครงสร้างต่างๆ
ALPHA	=	%x41-5A / %x61-7A   ; A-Z / a-z
BIT		=	"0" / "1"
CHAR	=	%x01-7F	; any 7-bit US-ASCII character, excluding NUL
CR		=	%x0D		; carriage return
CRLF	=	CR LF		; Internet standard newline
DIGIT	=	%x30-39	; 0-9
DQUOTE	=	%x22		; " (Double Quote)
HEXDIG	=	DIGIT / "A" / "B" / "C" / "D" / "E" / "F"
HTAB	= 	%x09		; horizontal tab
LF		=  	%x0A 		; linefeed
SP		=	%x20
VCHAR	=	%x21-7E	; visible (printing) characters
ALNUM	=	ALPHA / DIGIT
 
## CSP Message
### Message Type
 	CSP message ประกอบด้วย request-message (section 4) จาก client และ response-message (section 5) จาก server
 	 		CSP-message = request / response
 	ทั้ง request-message และ response-message มีรูปแบบที่เหมือนกันเรียกว่า generic-message โดยจะขึ้นต้น message ด้วย start-line ตามด้วยส่วนของ message-header และ message-body โดยมี body-sep เพื่อกั้นระหว่างส่วน header และ body และ end-sep เพื่อระบุจุดจบของ message
 	แม้ว่า request-message และ response-message จะมีรูปแบบที่เหมือนกัน แต่รายละเอียดของ start-line นั้นต่างกัน อีกทั้งข้อมูลใน message-header ก็ต่างกันอีกด้วย
 			generic-message = 	start-line CRLF
 						*(message-header CRLF) 	; section 3.2
 						body-sep CRLF
 						[*(message-body CRLF)]	; section 3.3
 						end-sep CRLF
 	 		start-line 	=	request-line / 		; section 4.1
 						status-line 		; section 5.1
 			body-sep 	= 	"BODY"
 			end-sep 	=	"END"
 	ทั้ง message-header และ message-body มีรูปแบบการเขียนที่เหมือนกัน คืออยู่ในรูปของ message-line ดังนี้
 			message-line 	= field-name ":" field-value
 			field-name 	= 1*ALNUM
 			field-value 	= *VCHAR
### Message Header
 	message header นั้นครอบคลุมทั้ง general-header (section 3.4) , request-header (section 4.3) และ response-header (section 5.3) โดย message header มีหน้าที่ในการเก็บข้อมูลสำคัญของแต่ล่ะ message
  	สำหรับ message-header นั้นลำดับไม่มีความสำคัญ และถ้ามี header ที่มี field-name ซ้ำกัน จะสนใจเพียงค่าล่าสุดเท่านั้น (ค่าที่อยู่ล่าง)

### Message Body
 	message body นั้นมีหน้าที่ในการเก็บข้อมูลธุรกรรมเพิ่มเติม สำหรับธุรกรรมแต่ล่ะประเภท โดยแต่ล่ะ message นั้นจะมี message body ไม่เหมือนกัน ขึ้นอยู่กับ header ที่ชื่อว่า btag (section 3.4.4) 
 	บาง message อาจไม่มี btag header ซึ่งนั้นก็จะหมายความว่าจะไม่มีส่วนของ message body ด้วยเช่นกัน
### General Header
 	general header คือ header ที่ใช้ทั้งใน request message และ response message มีดังนี้
 			general-header =	version-line /		; section 3.4.1
 						uid-line / 		; section 3.4.2
 						ts-line / 		; section 3.4.3
 						btag-line		; section 3.4.4
#### version
 	ใช้สำหรับระบุ version ของ CSP protocol ที่ใช้สร้าง message โดยเลข version จะแบ่งเป็น 2 ส่วน ได้แก่ major และ minor
 			version-line  		= "version" ":" version-number
 			version-number 	=  major "." minor
 			major			= 1*3DIGIT
 			minor 			= 1*3DIGIT
 	หาก message ที่ส่งมาไม่มี version-line จะถือว่า version นั้นเป็น version เดียวกับผู้รับ
#### user-id
 	ใช้สำหรับระบุเลข id ของผู้ทำธุรกรรม
 		uid-line = "uid" ":" 1*DIGIT
#### time-stamp
 	ถ้าใน request-message จะระบุเวลาที่สร้าง message แต่ถ้าใน response-message จะระบุ time-stamp ของ request-message เพื่อให้ client ใช้ในการจับคู่ response-message ที่ตอบกลับมากับ request-message ที่ส่งออกไป
 		ts-line 	= 	"ts" ":" time-format
 		time-format =	date "/" month "/" year SP hour "." minute "."   
 					second SP time-zone
 		date	=	2DIGIT
 		month	=	2DIGIT
 		year 	= 	4DIGIT
 		hour	= 	2DIGIT
 		minute 	=	2DIGIT
 		second	=	2DIGIT
 		time-zone	=	3ALPHA
#### btag
 	เป็น header ที่มีไว้เพื่อระบุรูปแบบของข้อมูลในส่วน body	
 		btag-line	=	"btag" ":" btag-name	
 		btag-name	   =	1*(ALNUM / SP / "-")	
 	โดย btag แต่ละชนิดจะนิยาม field-name ที่จะปรากฎใน message-body และ ผู้รับ message (ทั้ง client และ server) จะต้องรองรับการอ่านข้อมูลด้วย btag ที่ระบุไว้จึงจะสามารถตีความ message ได้อย่างสมบูรณ์	
 	โดย protocol นี้ได้มี build-in btag ไว้อยู่แล้วบางส่วน (section 6) แต่ผู้ imple-
ment ก็สามารถเพิ่มเติม btag เพื่อใช้งานเฉพาะด้านได้	
 	btag-line นั้นไม่ได้บังคับให้ต้องมีในทุก message หาก message ใดไม่มี btag ก็จะไม่มีข้อมูลในส่วน message-body (แม้ว่าจะมีแต่ผู้รับก็จะมองข้ามหากไม่มี btag)
