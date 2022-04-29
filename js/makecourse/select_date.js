
// 일정 추가 박스 생성
function makeLodgmentBox(number) {
    for(let i = 1; i <= number; i++) {
        $('#lodgment_box').append(
            `   
            <div id="lodgment_${i}" class="step-wrap">
                <div class="title-wrap">
                    <div class="title-type2">${i}일차 일정 ( 최대 5개까지 선택 가능 )</div>
                    <div style="float:right;">
                        <a class="btnEmFix sizeS route" style="color:rgb(255,255,255);cursor:pointer;">위치보기</a>
                        <a class="btnEmFix sizeS delete" style="color:rgb(255,255,255);cursor:pointer;">삭제</a>
                        <input type="hidden" class="delete_input" value="${i}"/>
                    </div>
                </div>
                <div class="tab-content active etcdelete">
                    <div class="menu-list-v2">
                        <ul>
                            <li>
                                <div class="title-type2">담은여행지</div>
                            </li>
                        </ul>
                        <ul id=course_date_${i}>
                            
                        </ul>
                    </div>
                </div> 
            </div><!-- 음료 선택 끝 -->
            `
        );
    }
}





let CDate = new Date(); 
let _today = new Date();


// 달력 만들기
let buildcalendar = function(){
	let htmlDates = ''; 
	let prevLast = new Date(CDate.getFullYear(), CDate.getMonth(), 0); //지난 달의 마지막 날 
	let thisFirst = new Date(CDate.getFullYear(), CDate.getMonth(), 1); //이번 달의 첫쨰 날
	let thisLast = new Date(CDate.getFullYear(), CDate.getMonth() + 1, 0); //이번 달의 마지막 날
	document.querySelector(".year").innerHTML = CDate.getFullYear() + "년";  // year에 년도 출력
	document.querySelector(".month").innerHTML = (CDate.getMonth() + 1) + "월";  //month에 월 출력
	const dates = []; 
	if(thisFirst.getDay()!=0){ 
		for(let j = 0; j < thisFirst.getDay(); j++){
			dates.unshift(prevLast.getDate()-j); // 지난 달 날짜 채우기
		} 
	} 
	for(let k = 1; k <= thisLast.getDate(); k++){
			 dates.push(k); // 이번 달 날짜 채우기 
	} 
	for(let l = 1; l <= 13 - thisLast.getDay(); l++){ 
			 dates.push(l); // 다음 달 날짜 채우기 (나머지 다 채운 다음 출력할 때 42개만 출력함)
	} 
	let flag = true;
	for(let i = 0; i < 42; i++){
		if(i < thisFirst.getDay()){
			htmlDates += '<div class="date last">'+dates[i]+'</div>'; 
		}else if(_today.getDate()==dates[i] && _today.getMonth()==CDate.getMonth() && _today.getFullYear()==CDate.getFullYear()){
            if(flag) {
                htmlDates += '<div id="date_'+dates[i]+'" class="date _today" onclick="fn_selectDate('+dates[i]+');">'+dates[i]+'</div>'; 
                flag = false;
            }else {
                htmlDates += '<div class="date next">'+dates[i]+'</div>'; 
            }
		}else if(i >= thisFirst.getDay() + thisLast.getDate()){
			 htmlDates += '<div class="date next">'+dates[i]+'</div>'; 
		}else{
			if(_today.getDate() < dates[i] && _today.getMonth() <= CDate.getMonth() && _today.getFullYear() <= CDate.getFullYear()){
                htmlDates += '<div id="date_'+dates[i]+'" class="date" onclick="fn_selectDate('+dates[i]+');">'+dates[i]+'</div>'; 
            }else {
                if(_today.getMonth() < CDate.getMonth() && _today.getFullYear() <= CDate.getFullYear()) {
                    htmlDates += '<div id="date_'+dates[i]+'" class="date" onclick="fn_selectDate('+dates[i]+');">'+dates[i]+'</div>'; 
                }else if(_today < CDate) {
                    htmlDates += '<div id="date_'+dates[i]+'" class="date" onclick="fn_selectDate('+dates[i]+');">'+dates[i]+'</div>'; 
                }else {
                    htmlDates += '<div class="date last">'+dates[i]+'</div>'; 
                }
            }
		}
	 } 
    document.querySelector(".dates").innerHTML = htmlDates; 
} 

// 저번달 보기
function prevCal(){
    CDate.setMonth(CDate.getMonth()-1); 
    buildcalendar(); 
} 
// 다음달 보기
function nextCal(){
    CDate.setMonth(CDate.getMonth()+1);
    buildcalendar(); 
}

let startDate = "";
let lastDate = "";
let selectDate = 0;
let selectMonth = 0;
let selectYear = 0;

// 달력에서 날짜를 눌렀을때의 함수
function fn_selectDate(date){
   
   let year = CDate.getFullYear();
   let month = CDate.getMonth() + 1;
   let date_txt = "";
   
   if(CDate.getMonth + 1 < 10){
       month = "0" + (CDate.getMonth() + 1);
   }
   if(date < 10){
       date_txt = "0" + date;
   }

   if(selectDate === 0 && selectMonth === 0 && selectYear == 0){
       $('#lodgment_combobox').empty(); // 콤보박스 초기화
       $('#show_lodgment').empty(); // n박n일 글자 초기화
       $('#lodgment_box').empty(); // 일정 박스 초기화
       overlapCheckList = []; // 중복체크 리스트 초기화

       $(".date").css("background-color", "");
       $(".date").css("color", "");
       $("#date_"+date).css("background-color", "red");
       $("#date_"+date).css("color", "white");
       
       $("#period_1").val(year+"-"+month+"-"+date); //시작 날짜
       $("#period_2").val('');

       selectDate = date;
       selectMonth = month;
       selectYear = year;
       startDate = new Date(year, (month-1), date);
   }else if(selectDate === date && selectMonth === month && selectYear === year) {
       // 같은곳을 한번 더 눌렀을때 처리
       $("#date_"+date).css('background-color', '');
       $("#date_"+date).css('color', '');
       $("#period_1").val('');
       $("#period_2").val('');
       selectDate = 0;
       selectMonth = 0;
       selectYear = 0;
   }else{
       if(selectDate > date && selectMonth >= month && selectYear >= year) {
           // 두번째 선택할 때 처음 선택한 날짜보다 과거의 날짜를 골랐을 때
           $('#lodgment_combobox').empty(); // 콤보박스 초기화
           $('#show_lodgment').empty(); // n박n일 글자 초기화
           $('#lodgment_box').empty(); // 일정 박스 초기화

           $(".date").css("background-color", "");
           $(".date").css("color", "");
           $("#date_"+date).css("background-color", "red");
           $("#date_"+date).css("color", "white");
           
           $("#period_1").val(year+"-"+month+"-"+date); //시작 날짜
           $("#period_2").val('');

           selectDate = date;
           selectMonth = month;
           selectYear = year;
           // month 가 자꾸 new Date 해서 생성하면 +1 된 값이 나와서 -1 했다.
           startDate = new Date(year, (month-1), date); // 시작 날짜
       }else {
            lastDate = new Date(year, (month-1), date); // 끝 날짜 
            let startLodgment = lastDate.getTime() - startDate.getTime();
            startLodgment = startLodgment / (1000*60*60*24); // 일자수 계산
            let lastLodgment = startLodgment + 1;
            // 큰달에서 작은 숫자를 고르고 작은달에서 큰숫자를 골랐을 때 마이너스 날짜가 되는거 방지 + 30일 이상 선택 불가
            if(lastDate < startDate || startLodgment > 30 ) {
                 // 두번째 선택할 때 처음 선택한 날짜보다 과거의 날짜를 골랐을 때
                $('#lodgment_combobox').empty(); // 콤보박스 초기화
                $('#show_lodgment').empty(); // n박n일 글자 초기화
                $('#lodgment_box').empty(); // 일정 박스 초기화

                $(".date").css("background-color", "");
                $(".date").css("color", "");
                $("#date_"+date).css("background-color", "red");
                $("#date_"+date).css("color", "white");
                
                $("#period_1").val(year+"-"+month+"-"+date); //시작 날짜

                selectDate = date;
                selectMonth = month;
                selectYear = year;
                // month 가 자꾸 new Date 해서 생성하면 +1 된 값이 나와서 -1 했다.
                startDate = new Date(year, (month-1), date); // 시작 날짜
            }else {
                // 정상적으로 두번째 날짜를 골랐을 때
                $("#date_"+date).css("background-color", "red");
                $("#date_"+date).css("color", "white");
                
                

                let startMonth = startDate.getMonth();
                let lastMonth = lastDate.getMonth();

                if(lastMonth > startMonth){
                    for(let i = date - 1; i > 0; i--){
                        $("#date_"+i).css("background-color", "#FFDDDD");
                    }
                    $(".last").css("background-color", "#FFDDDD");
                }else {
                    for(let i = selectDate + 1 ; i < date ; i++){
                        $("#date_"+i).css("background-color", "#FFDDDD");
                    }
                }
                
                $("#period_2").val(year+"-"+month+"-"+date); // 끝 날짜

                $('#show_lodgment').append(`${startLodgment}박 ${lastLodgment}일`); // n박n일 글자 띄우기
                
                // 콤보박스 생성
                for(let i = 1; i <= lastLodgment; i++) {
                    if(i === 1) {
                        $('#lodgment_combobox').append(
                            `<option value="${i}" selected>Day ${i}</option>`
                        );
                    }else {
                        $('#lodgment_combobox').append(
                            `<option value="${i}">Day ${i}</option>`
                        );
                    }
                    addOverlapCheckList(i);
                }
                
                // 일정 박스 생성
                makeLodgmentBox(lastLodgment);

                selectDate = 0;
                selectMonth = 0;
                selectYear = 0;
            }
          
       }
       
   }
   
}

// 달력보기, 달력 숨기기
$(document).ready(function() {
   buildcalendar();
   let flag = false;
   $('#date_btn').click(function() {
       
       if(flag) {
           $('#set_calendar').hide();
           flag = false;
       }else {
           $('#set_calendar').show();
           flag = true;
       }
       
   });
});


