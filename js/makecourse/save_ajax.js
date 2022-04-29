// 세이브 ajax
$(document).ready(function() {
    $('#save_course').click(function() {
        const comboboxNum = $('#lodgment_combobox').val();
        const courseName = $('#course_name').val();
        const courseInfo = $('#course_info').val();
        const courseNameCheck = $('#find_cname').text();

        if(!courseName) {  // 코스이름 입력 체크
            alert("코스이름을 입력해주세요");
            location.href = "#content";
            return;
        }
        if(!courseInfo) {  // 코스설명 입력 체크
            alert("코스설명을 작성해주세요");
            location.href = "#content";
            return;
        }

        if(courseNameCheck != '사용가능한 코스 이름 입니다.') {
            alert("코스이름을 사용할 수 없습니다.");
            location.href = "#content";
            return;
        }
        
        if(comboboxNum) { // 일정이 추가되어 있는지 체크
            const maxNum = $('#lodgment_combobox option:last-child').val();
            // 컨텐츠 값 셋팅
            const courseMapList = [];
            const divisionList = [];
            const startDate = $("#period_1").val();
            const lastDate = $("#period_2").val();
            
            for(let i = 0; i < maxNum; i++) {
                let courseDate = $(`#course_date_${i+1}`);
                let contentLength = courseDate.children().length;
                const dataList = [];
                
                const courseMap = {
                    lodgment: "",
                    contentList: []
                };
                if(contentLength > 0) { // 일정안에 컨텐츠가 하나도 들어있지 않은곳이 있는지 체크
                    for(let j = 0; j < contentLength; j++){
                        let contentName = courseDate.find('.content_subject').eq(j).text();
                        let contentDivision = courseDate.find('.division_input').eq(j).val();
                        if(contentName) {
                            dataList.push(contentName);
                            divisionList.push(contentDivision);
                        }
                    }
                }else {
                    alert("먼저 코스일정을 완성해주세요");
                    return;
                }
                courseMap.lodgment = String(i+1);
                courseMap.contentList = dataList;
                courseMapList.push(courseMap);
            }
            // 해시 태그 값 셋팅
            const hashTagLsit = [];

            const hashTagNum = $('#hashtag_list').children().length;
            const mainHash = $('.tag:checked').val();
            if(!mainHash) {
                alert("메인태그를 골라주세요");
                location.href = "#course_info";
                return;
            }
            hashTagLsit.push(mainHash);
            if(hashTagNum) {
                for(let i = 0; i < hashTagNum; i++) {
                    let hashTag = $(`#dough${i}`).val();
                    if(hashTag === mainHash) {
                        continue;
                    }else {
                        hashTagLsit.push(hashTag);
                    }
                }
            }else {
                hashTagLsit.push("없음");
            }

            // 예상 금액 가져오기
            const ccost = $('#course_ccost').val();

            // 닉네임 가져오기
            const nick = $('#nick').val();
            
            let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
			let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
			let csrfToken = $('meta[name="_csrf"]').attr('content');
            $.ajax({  // ajax 로 필요한 데이터 전송
                url: "/jejufriends/make_course/saveCourse.json",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({nick: nick, cname:courseName, ctaglist:hashTagLsit, cintro: courseInfo, divisionlist: divisionList, ccost: ccost, startdate: startDate, lastdate: lastDate, coursemaplist: courseMapList}),
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                }, 
                success: function(flag) {
                    if(flag){
                        alert("코스 작성 완료!");
                        location.href = "/jejufriends/select_course/select.do";
                    }else {
                        alert("코스 저장중 문제 발생");
                    }
                },
                error: function() {
                    alert("코스 저장중 문제가 발생 했습니다 \n 다시 시도해 주세요");
                }
            });   
        }else {
            alert("먼저 코스일정을 완성해주세요");
        }
    });
});