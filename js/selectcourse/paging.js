
let selectPage = 1;
let maxPage = 0;
let courseListTemp = [];
function pageSet(pageNum, page, courseList) {
    $('#paging').empty();
    let pageHtml = '';
    let startPage = 1;
    let loop = 0;
    // courseList 가 배열이면 새로 전역변수에 대입
    if(Array.isArray(courseList)) courseListTemp = courseList;

    if(isNaN(pageNum)) {
        alert("숫자 아님");
    }

    // 마지막 페이지 계산
    maxPage = Math.floor(courseListTemp.length / pageNum);
    if(courseListTemp.length%pageNum != 0) maxPage++;
    selectPage = page;

    // floor === 소수점 버림, toFixed(0) === 소수점 몇번째 자리까지 자른건지
    // 시작 페이지
    if((page/pageNum).toFixed(0) != 0) {
        loop = Math.floor(page/pageNum+1);
        if(pageNum*(loop-1) == page){
            startPage = Math.floor(pageNum*(loop-2)+1);
            loop -= 1;
        }else{
            startPage = Math.floor(pageNum*(loop-1)+1);
        }
    }else {
        loop = 1;
    }

    // 코스 리스트 출력
    let courseHtml = '';
    let courseListStart = page*pageNum-pageNum;
    let courseListLast = page*pageNum;

    $("#course_list").empty();
    if(courseListTemp.length != 0) {
        for(let i = courseListStart; i < courseListLast; i++) {
            if(courseListTemp[i] != null){
                let course = courseListTemp[i];
                courseHtml += `
                    <tr style="background-color:#FFFFFF; color:#555555;" class="">
                        <td>${course.cnum}</td>
                        <td class="subject" style="text-align: center;">
                            <a href="/jejufriends/select_course/courseContent.do?cnum=${course.cnum}" style="color:#555555;">${course.cname}</a> 
                            <img src="http://img.echosting.cafe24.com/design/skin/admin/ko_KR/ico_attach2.gif"  alt="파일첨부" class="ec-common-rwd-image"/>
                            <span></span>
                        </td>
                        <td class="displaynone"></td>
                        <td class="subject">
                            <span class="displaynone">
                                <a href="#none" onclick="BOARD.viewTarget('16','5',this);">
                                <img src="http://img.echosting.cafe24.com/skin/base_ko_KR/board/btn_unfold.gif" alt="내용 보기"/></a>
                            </span>
                            <a href="/jejufriends/select_course/courseContent.do?cnum=${course.cnum}" style="color:#555555;">${course.cintro}</a> 
                        </td>
                        <td>${course.nick}</td>
                        <td class="txtLess ">${course.cdate}</td>
                        <td class="txtLess ">${course.views}</td>
                        <td class="txtLess ">${course.choosed}</td>
                        <td class="displaynone"><img src="http://img.echosting.cafe24.com/skin/base_ko_KR/board/ico_point0.gif" alt="0점"/></td>
                    </tr>
                `;
            }
        }
    }else {
        courseHtml += `
            <tr style="background-color:#FFFFFF; color:#555555;" class="">
                <td colspan="7" class="subject" style="text-align: center;">검색 결과가 없습니다</td>
            </tr>
        `;
    }
    $("#course_list").append(courseHtml);

    // 페이지 버튼 생성
    if(selectPage > 1) {
        pageHtml += '<a><img class="beforePage" style="cursor:pointer;" src="//img.echosting.cafe24.com/skin/base/common/btn_page_prev.gif" alt="이전 페이지"/></a>';
    }
    pageHtml += '<ol>';
    for(let i = startPage; i <= startPage+5; i++) {
        if(i === page) pageHtml += `<li class="xans-record-"><a class="this" style="cursor:pointer;">${i}</a></li>`;
        else if(i > maxPage) break;
        else pageHtml += `<li class="xans-record-"><a class="page" style="cursor:pointer;">${i}</a></li>`;
    }
    pageHtml += '</ol>';
    if(selectPage != maxPage && maxPage != 0) {
        pageHtml += '<a><img class="nextPage" style="cursor:pointer;" src="//img.echosting.cafe24.com/skin/base/common/btn_page_next.gif" alt="다음 페이지"/></a>';
    }
    $('#paging').append(pageHtml);
}

$(document).ready(function() {
    // 페이지 번호 누를 때 이벤트
    $(document).on('click', '.page', function() {
        const page = Number($(this).text());
        const pageNum = $('#pageNum option:selected').val();
        pageSet(pageNum, page, null);
    });

    // 다음페이지 버튼 누를 때 이벤트
    $(document).on('click', '.nextPage', function() {
        const page = selectPage + 1;
        const pageNum = $('#pageNum option:selected').val();
        pageSet(pageNum, page, null);
    });

    // 이전페이지 버튼 누를 때 이벤트
    $(document).on('click', '.beforePage', function() {
        const page = selectPage - 1;
        const pageNum = $('#pageNum option:selected').val();
        pageSet(pageNum, page, null);
    });

    // 페이지 수 변경할 때 이벤트
    $(document).on('change', '#pageNum', function() {
        const page = selectPage;
        const pageNum = $('#pageNum option:selected').val();
        pageSet(pageNum, page, null);
    });

    // 검색 이벤트
    $('#select_button').on('click', function() {
        selectSearchAjax();
    });
    $('#select_search').on('keydown', function(key) {
        if(key.keyCode == 13) {
            selectSearchAjax();
        }
    });
});

// 검색 ajax
function selectSearchAjax() {
    const searchKey = $('#search_key option:selected').val();
    const keyWord = $('#select_search').val();

    let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
    let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
    let csrfToken = $('meta[name="_csrf"]').attr('content');
    $.ajax({
        url: "/jejufriends/select_course/selectSearch.json",
        type: "POST",
        data: {column: searchKey, keyword: keyWord},
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(courseList) {
            const pageNum = $('#pageNum option:selected').val();
            pageSet(pageNum, 1, courseList);
        }
    });
}