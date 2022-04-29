
let overlapCheckList = [];

// 콤보박스가 생성될 때 중복 제거를 위해 콤보박스 갯수만큼 배열을 셋팅하는 메서드
function addOverlapCheckList(comboboxIndex) {
    const overlapCheck = {
        comboboxIndex: 0,
        listNameList: [],
    };
    overlapCheck.comboboxIndex = comboboxIndex;
    overlapCheckList.push(overlapCheck);
}


// 검색된 컨텐츠 선택했을 때 색 변경
const make_search_content_map = new Map();
$(document).ready(function() {
    $(document).on('click', '.make_search_content', function() {
        let contentName = $(this).children('.prd-cont').children('.content_subject').text();
        
        if(make_search_content_map.has(contentName)) {
            let makeCourseContentFlag = make_search_content_map.get(contentName);
            if(makeCourseContentFlag) {
                $(this).css('background-color', 'rgba(81,127,226,0.1)');
                make_search_content_map.set(contentName, false);
            }else {
                $(this).css('background-color', '');
                make_search_content_map.delete(contentName);
            }
        }else {
            if(make_search_content_map.size < 5) {
                $(this).css('background-color', 'rgba(81,127,226,0.1)');
                make_search_content_map.set(contentName, false);
            }else {
                alert("하루당 최대값은 5개 입니다.");
            }
        }
    });
});

// 일정에 들어있는 컨텐츠 선택했을 때 색 변경
const make_course_content_map = new Map();
$(document).ready(function() {
    $(document).on('click', '.make_course_content', function() {
        let contentName = $(this).children('.prd-cont').children('.content_subject').text();
        
        // has() 는 맵이 파라미터에 넣은 키 값을 가지고 있는지 확인하는 메소드
        if(make_course_content_map.has(contentName)) {
            let makeCourseContentFlag = make_course_content_map.get(contentName);
            if(makeCourseContentFlag) {
                $(this).css('background-color', 'rgba(81,127,226,0.1)');
                make_course_content_map.set(contentName, false);
            }else {
                $(this).css('background-color', '');
                make_course_content_map.delete(contentName);
            }
        }else {
            $(this).css('background-color', 'rgba(81,127,226,0.1)');
            make_course_content_map.set(contentName, false);
        }
    });
});


// 검색 컨텐츠 전부 선택 해체
function contentColorReset() {
    if(make_search_content_map) {
        let contentnameList = make_search_content_map.keys();
        for(let contentName of contentnameList) {
            contentName = contentName.replace(/(\s*)/g, "");
            $(`#search_${contentName}`).css('background-color', '');
        }
        make_search_content_map.clear();
    }
}


// 일정 추가 이벤트
$(document).ready(function() {
    $("#add_calendar").click(function() {
        const comboboxIndex = $('#lodgment_combobox option:selected').val();
        if(comboboxIndex) {
            let flag = false;
            const overlapCheck = overlapCheckList[comboboxIndex-1];
            const listNameList = overlapCheck.listNameList;
            console.log("listNameList: "+listNameList);
            if(listNameList){
                for(let contentName of listNameList) {
                    if(make_search_content_map.has(contentName)) {
                        flag = true;
                    }
                }
            }else {
                flag = true;
            }
            if(!flag) {
                let contentnameList = make_search_content_map.keys();
                let contentAmount = $(`#course_date_${comboboxIndex}`).children().length; // 자식요소의 갯수
                const limiter = contentAmount + make_search_content_map.size;
                if(limiter < 6) {
                    let objectIndex = -1;
                    let makeCourse = null;
                    if(makeCourseList) {
                        for(let contentname of contentnameList) {
                            objectIndex = makeCourseList.findIndex((object) => object.contentname == contentname);
                            makeCourse = makeCourseList[objectIndex];
                            // .replace(/^\s+|\s+$/gm,''); 스트링 앞뒤 공백 제거 정규식
                            // .replace(/(\s*)/g, ""); 스트링 모든공백 제거( 문자 사이 공백도 ) 정규식
                            let contentName = makeCourse.contentname.replace(/(\s*)/g, "");
                            $(`#course_date_${comboboxIndex}`).append(
                                `
                                <li id="calendar_day_${comboboxIndex}_${contentName}" class="make_course_content make_content" >
                                    <input type="hidden" class="content_input" value="${makeCourse.contentaddress}"/>
                                    <input type="hidden" class="division_input" value="${makeCourse.contentdivision}"/>
                                    <input type="hidden" class="set_amount" value="${makeCourse.contentcost}"/>
                                    <div class="prd-img">
                                        <img class="lazyload" style="width:100%; height:100%;" src="/photo/${makeCourse.contentphoto}"/>
                                    </div>
                                    
                                    <div class="prd-cont">
                                        <div class="subject content_subject" style="cursor:pointer;  display:inline-block;">${makeCourse.contentname}</div>
                                        <br/>
                                        <div class="price-box" style="display:inline-block;">
                                            <strong>가격: ${makeCourse.contentcost}</strong>
                                        </div>
                                    </div>
                                </li>
                                `
                            );
                            // 추가 눌렀을 때 그 위치로 이동
                            if(comboboxIndex > 1) {
                                location.href=`#lodgment_${comboboxIndex-1}`;
                            }else {
                                location.href='#lodgment_top';
                            }
                            listNameList.push(contentname);
                            overlapCheckList[comboboxIndex-1].listNameList = listNameList;
                        }
                        contentColorReset();
                        setAmount();
                    }
                }else{
                    alert("하루당 최대값은 5개 입니다.");
                }
            }else {
                alert("일정에 중복된 컨텐츠를 추가할 수 없습니다.");
            }
        }
    });
});

// 일정 삭제 이벤트
$(document).ready(function() {
    $(document).on('click', '.delete', function() {
        if(make_course_content_map.size > 0){
            const lodgmentNum = $(this).siblings('.delete_input').val(); // siblings() 는 형제 태그를 모두 찾는 메소드이다.
            const contentNameList = make_course_content_map.keys();
            for(let contentname of contentNameList) {
                let contentName = contentname.replace(/(\s*)/g, "");
                $(`#calendar_day_${lodgmentNum}_${contentName}`).remove();
                let overlapCheck = overlapCheckList[lodgmentNum-1];
                let listNameList = overlapCheck.listNameList;
                let newListNameList = listNameList.filter((listName) => listName !== contentname); // filter()는 삭제된 listIndex 만 빼고 새로운 배열로 만들어서 리턴해줌
                overlapCheck.listNameList = newListNameList;
                overlapCheckList[lodgmentNum-1] = overlapCheck;
                // 삭제된 컨텐츠들 맵에서 빼주기
                make_course_content_map.delete(contentname);
            }
        }else {
            alert("삭제할 컨텐츠를 선택해주세요.");
        }
        
    });
});

function setAmount() {
    const maxNum = $('#lodgment_combobox option:last-child').val();
    let maxAmount = 0;
    for(let i = 0; i < maxNum; i++) {
        let courseDate = $(`#course_date_${i+1}`);
        let contentLength = courseDate.children().length;
        if(contentLength > 0) { // 일정안에 컨텐츠가 하나도 들어있지 않은곳이 있는지 체크
            for(let j = 0; j < contentLength; j++){
                let amount = courseDate.find('.set_amount').eq(j).val();
                if(amount.indexOf('원')) {
                    amount = amount.substring(amount.indexOf('원')-1);
                }
                console.log("amount: " + amount);
                if(amount) {
                    maxAmount += Number(amount);
                }
            }
        }
    }
    $('#course_ccost_text').text(`${maxAmount}원`);
    $('#course_ccost').val(maxAmount);
}