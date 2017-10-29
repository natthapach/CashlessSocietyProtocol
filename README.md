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
  ใช้สำหรับการนิยาม rule โดยที่ name คือชื่อของ rule, elements คือ rule หรือ terminal และ crlf คือ carriage return ตามด้วย line feed
#### Terminal Values
	name = % base code
  ใช้สำหรับนิยาม terminal โดยที่ name คือชื่อของ terminal. base คือฐานของเลข code และ code คือตัวเลขแทนตัวอักษรใน ASCII
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
  ใช้สำหรับสร้างทางเลือก เช่น foo / bar หมายถึง foo หรือ bar
#### Sequence Group
	(element1 element2)
  ใช้สำหรับจัดกลุ่มของ element
#### Variable Repetition
	<a>*<b>element
  ใช้สำหรับการซ้ำ element อย่างน้อย a จนถึงอย่างมาก b เช่น 1*2element โดยค่าพื้นฐานของ a=0 และ b=infinity
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
  - ALPHA		=	%x41-5A / %x61-7A   ; A-Z / a-z
  - BIT			=	"0" / "1"
  - CHAR		=	%x01-7F	; any 7-bit US-ASCII character, excluding NUL
  - CR			=	%x0D		; carriage return
  - CRLF		=	CR LF		; Internet standard newline
  - DIGIT	=	%x30-39	; 0-9
  - DQUOTE	=	%x22		; " (Double Quote)
  - HEXDIG	=	DIGIT / "A" / "B" / "C" / "D" / "E" / "F"
  - HTAB	= 	%x09		; horizontal tab
  - LF		=  	%x0A 		; linefeed
  - SP		=	%x20
  - VCHAR	=	%x21-7E	; visible (printing) characters
  - ALNUM	=	ALPHA / DIGIT
 
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
	start-line 	=		request-line / 		; section 4.1
 						status-line 		; section 5.1
 	body-sep 	= 		"BODY"
 	end-sep 	=		"END"
ทั้ง message-header และ message-body มีรูปแบบการเขียนที่เหมือนกัน คืออยู่ในรูปของ message-line ดังนี้

	message-line 	= field-name ":" field-value
 	field-name 		= 1*ALNUM
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
 						uid-line / 			; section 3.4.2
 						ts-line / 			; section 3.4.3
 						btag-line			; section 3.4.4
#### version
ใช้สำหรับระบุ version ของ CSP protocol ที่ใช้สร้าง message โดยเลข version จะแบ่งเป็น 2 ส่วน ได้แก่ major และ minor

 	version-line  		= "version" ":" version-number
 	version-number 		=  major "." minor
 	major				= 1*3DIGIT
 	minor 				= 1*3DIGIT
หาก message ที่ส่งมาไม่มี version-line จะถือว่า version นั้นเป็น version เดียวกับผู้รับ
#### user-id
ใช้สำหรับระบุเลข id ของผู้ทำธุรกรรม

	uid-line = "uid" ":" 1*DIGIT
#### time-stamp
ถ้าใน request-message จะระบุเวลาที่สร้าง message แต่ถ้าใน response-message จะระบุ time-stamp ของ request-message เพื่อให้ client ใช้ในการจับคู่ response-message ที่ตอบกลับมากับ request-message ที่ส่งออกไป

	ts-line 	= 	"ts" ":" time-format
 	time-format =	date "/" month "/" year SP hour "." minute "." second SP time-zone
 	date		=	2DIGIT
 	month		=	2DIGIT
 	year 		= 	4DIGIT
 	hour		= 	2DIGIT
 	minute 		=	2DIGIT
 	second		=	2DIGIT
 	time-zone	=	3ALPHA
#### btag
 เป็น header ที่มีไว้เพื่อระบุรูปแบบของข้อมูลในส่วน body	
 
 	btag-line	=	"btag" ":" btag-name	
 	btag-name	   =	1*(ALNUM / SP / "-")	
        
 โดย btag แต่ละชนิดจะนิยาม field-name ที่จะปรากฎใน message-body และ ผู้รับ message (ทั้ง client และ server) จะต้องรองรับการอ่านข้อมูลด้วย btag ที่ระบุไว้จึงจะสามารถตีความ message ได้อย่างสมบูรณ์	
 
โดย protocol นี้ได้มี build-in btag ไว้อยู่แล้วบางส่วน (section 6) แต่ผู้ imple-
ment ก็สามารถเพิ่มเติม btag เพื่อใช้งานเฉพาะด้านได้	

 btag-line นั้นไม่ได้บังคับให้ต้องมีในทุก message หาก message ใดไม่มี btag ก็จะไม่มีข้อมูลในส่วน message-body (แม้ว่าจะมีแต่ผู้รับก็จะมองข้ามหากไม่มี btag)
 
## Request
### Request-Line
request-line นั้นมีไว้สำหรับบอกประเภทของ request-message ซึ่งส่งผลต่อการกระทำของ server ต่อ message โดย request-line มีโครงสร้างดังนี้

 	request-line	=	"method" ":" method
#### Method
 method เป็นส่วนที่ใช้ในการระบุชนิดของ request-message
 		
     method	=	"DEPOSIT" 	/		
 				"WITHDRAW"	/
 				"TRANSFER"	/
 				"PAY"		/
 				"BALANCE"
#### DEPOSIT
 เป็น method ที่ใช้สำหรับเพิ่มยอดเงินคงเหลือให้กับผู้ทำธุรกรรม
#### WITHDRAW
 เป็น method ที่ใช้สำหรับลดยอดเงินคงเหลือให้กับผู้ทำธุรกรรม
#### TRANSFER
 เป็น method ที่ใช้สำหรับโอนย้ายเงินระหว่างบัญชี
#### PAY
 เป็น method ที่ใช้สำหรับหักยอดเงินคงเหลือของผู้ทำธุรกรรมเพื่อซื้อสินค้าหรือบริการ โดยทฤษฎีแล้ว PAY นั้นถูกออกแบบมาให้แยกออกมาจาก TRANSFER เพื่อที่จะสามารถเป็นข้อมูลในการเก็บภาษี
#### BALANCE
 เป็น method ที่ใช้สำหรับสอบถามยอดเงินคงเหลือของผู้ทำธุรกรรม
### Request Header 
header ดังต่อไปนี้เป็น header สำหรับ request message
 		
    request-header	=	agent-line	/
 						sid-line	/
 						amt-line
#### agent
ใช้สำหรับระบุ program ที่ผู้ทำธุรกรรมใช้ในการส่ง message (agent อาจเป็นความลับระหว่าง client และ server เพื่อป้องกันการปลอมแปลงข้อมูลได้)

 	agent-line = "agent" ":" 1*(ALNUM / SP)
#### service-id
มีโครงสร้างดังนี้

	sid-line = "sid" ":" 1*DIGIT
โดยมีความหมายแตกต่างกันตาม method ดังนี้
##### สำหรับ DEPOSIT และ WITHDRAW 
จะทำหน้าที่ในการระบุบริษัทเจ้าของบัญชีของผู้ทำธุรกรรม
###### สำหรับ TRANSFER 
จะทำหน้าที่ในการระบุบัญชีปลายทาง
##### สำหรับ PAY
จะทำหน้าที่ในการระบุร้านค้าที่ทำการซื้อสินค้า / บริการ
#### amount
ใช้เพื่อระบุยอดเงินในการทำธุรกรรม
 		
    amt-line = "amt" ":" 1*DIGIT["." 1*DIGIT]
    
##	Response
### Status-Line
ใช้ในการระบุผลของธุรกรรม โดยมีโครงสร้างดังนี้

 	status-line = "status" ":" (status-code SP status-phrase)
### Status Code and Status Phrase
status code และ status phrase เป็นชุดข้อความเพื่อแสดงถึงผลของการดำเนินธุรกรรม ว่าสำเร็จหรือพบปัญหาใดๆหรือไม่

 	status-code 	= 3*DIGIT
 	status-phrase 	= 1*(ALPHA / SP)
status code และ status phrase มีดังนี้
#### 200 OK
ผลของการดำเนินธุรกรรมเรียบร้อย ไม่พบปัญหา
#### 400 Bad Request
request message ที่ส่งมาจาก client ไม่ถูกต้อง อาจมีบาง field ขาดหายไป
#### 404 ID not found
ค้นหา uid หรือ sid ที่ระบุมาใน request header ไม่พบ
#### 418 Insufficient fund
ยอดคงเหลือไม่เพียงพอสำหรับการทำธุรกรรม
#### 505 version not support
server ไม่รองรับ version ของ request-message ที่ส่งมา
#### 506 BTAG not support
server ไม่รองรับ btag ของ request-message ที่ส่งมา
### Response Header Field
 header ต่อไปนี้สำหรับ response message
 
 	response-header = balance-line
#### balance
ใช้ระบุยอดเงินคงเหลือหลังจากทำธุรกรรม
 	balance-line = "balance" ":" 1*DIGIT["." 1*DIGIT]
    
## Build-in BTAG
### shop
#### message type
request message, PAY method
#### purpose
ใช้สำหรับข้อมูลการซื้อขาย
#### format
	btag:shop
#### body fields
##### purchase-count
ใช้เพื่อระบุจำนวนรายการซื้อ

 	purchase-count = "purchase-count" ":" 1*DIGIT
##### purchases
ใช้เพื่อระบุรายละเอียดของรายการซื้อ โดยที่ item-name คือชื่อของสินค้า, amt คือปริมาณของสินค้าชนิดนั้น และ price คือราคาต่อหน่วยของสินค้านั้นๆ

 	purchase 	= "purchase" ":" item-name SP amt SP price
 	item-name 	= 1*ALNUM 
 	amt 		= 1*DIGIT
 	price		= 1*DIGIT ["." 1*DIGIT]
purchase field สามารถมีได้หลายบรรทัดโดยไม่ถูกอ่านทับข้อมูลเดิม
### transport
#### message type
request message, PAY method
#### purpose
ใช้สำหรับการชำระเงินในระบบขนส่งมวลชนต่างๆ เช่น รถไฟฟ้า, รถประจำทาง
#### format
	btag:transport
#### body fields
##### source-station
ใช้เพื่อระบุสถานีต้นทาง 

 	source-station = "station" ":" station-id
 	station-id = 1*ALNUM
##### destination-station
ใช้เพื่อระบุสถานีปลายทาง 

 	destination-station = "dstation" ":" station-id
 	station-id = 1*ALNUM
### status 400
##### message type
response message, status 400
#### purpose
มีไว้เพื่อระบุข้อบกพร่องของ request-message
#### format
 	btag:status 400
#### body fields
##### missing
ใช้เพื่อระบุ field ที่หายไปใน request-message

 	missing = "missing" ":" field-name
##### wrong
ใช้เพื่อระบุ field ใน request-message ที่มีรูปแบบไม่ถูกต้อง

	wrong = "wrong" ":" field-name
###	status 404
####	message type
 	response-message, status 404
#### purpose
ใช้เพื่อระบุ id ที่ server หาไม่พบ
#### format
 	btag:status 404
#### body fields
##### missing
ใช้เพื่อระบุ id ที่ server หาไม่พบ

	missing = "missing" ":" 1*DIGIT
    
## Summary
### Request-message
 	"method:" method CRLF 					; section 4.2
 	["version:" version-number CRLF] 		; section 3.4.1
 	"uid:" 1*DIGIT CRLF 					; section 3.4.2
 	"ts:" time-format CRLF 					; section 3.4.3
 	"agent:" 1*(ALNUM / SP) 	 			; section 4.3.1
 	"amt:" 1*DIGIT["." 1*DIGIT CRLF] 		; section 4.3.3
 	["sid:" 1*DIGIT CRLF] 					; section 4.3.2
 	["btag:" btag CRLF] 					; section 3.4.4
 	"BODY" CRLF 						
 	[*(field-name ":" field-value CRLF)] 	; section 3.3
 	"END" CRLF 		
### Response-message
 	"status:" status-code SP status-phrase CRLF	; section 5.1
 	["version:" version-number CRLF]			; section 3.4.1
 	["balance:" 1*DIGIT["." 1*DIGIT] 			; section 5.3.1
 	"uid:" 1*DIGIT CRLF 						; section 3.4.2
 	"ts:" time-format CRLF 						; section 3.4.3
 	["btag:" btag CRLF] 						; section 3.4.4
 	"BODY" CRLF 					
 	[*(field-name ":" field-value CRLF)] 		; section 3.3
 	"END" CRLF
    
  
## Reference
Cashless society. แหล่งที่มา:https://en.wikipedia.org/wiki/Cashless_society#cite_note-uk- 
 	cashless-3, 12 ตุลาคม 2560.
    
Croker, D., Overell, P., Augmented BNF for Syntax Specifications: ABNF, RFC 5234, 
 	January 2008.
    
Fielding, R., Gettys, J., Mogul, J., Frystyk, H., Masinter, L., Leach P., Berners-Lee, T., Hypertext 
 	Transfer Protocol – HTTP/1.1, RFC 2616, June 1999.





