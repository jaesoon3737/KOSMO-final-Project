
let makeCourseList = []; // 검색되어서 ajax 로 넘어온 값을 넣어줄 변수
// 검색 ajax
$(document).ready(function() {
    let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
    let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
    let csrfToken = $('meta[name="_csrf"]').attr('content');
    $('#course_search_id').keyup(function(key) {
        $('#course_search_append').empty();
        $.ajax({
            url: "/jejufriends/make_course/search.json",
            type: "POST",
            data: {keyword: $('#course_search_id').val()},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },  
            success: function(data) {
                if(data.length) {
                    if(data[0].contentname === "#에러") {
                        alert("검색중 문제가 발생했습니다");
                        window.location.reload();
                    }else{
                        let searchHtml = '';
                        makeCourseList = data;
                        for(let makeCourse of makeCourseList) {
                            let contentName = makeCourse.contentname.replace(/(\s*)/g, "");
                            searchHtml += `
                                <li id="search_${contentName}" class="make_search_content make_content">
                                    <input type="hidden" class="content_input" value="${makeCourse.contentaddress}"/>
                                    <div class="prd-img">
                                        <img class="" style="width:100%; height:100%;" src="/photo/${makeCourse.contentphoto}"/>
                                    </div>
                                    
                                    <div class="prd-cont">
                                        <div class="subject content_subject" style="cursor:pointer; display:inline-block;">${makeCourse.contentname}</div>
                                        <br/>
                                        <div class="price-box" style="display:inline-block;">
                                            <strong>가격: ${makeCourse.contentcost}</strong>
                                        </div>
                                    </div>
                                </li>
                                `;
                        }
                        if(searchHtml.length === 0) {
                            $('#course_search_append').append(`
                                <li>
                                    <div class="prd-img"></div>
                                    <div class="prd-cont">
                                    <div class="subject">검색 결과가 없습니다</div>
                                    </div>
                                </li>
                                `
                            );
                        }else {
                            $('#course_search_append').append(searchHtml);
                        }
                    }
                }else {
                    $('#course_search_append').append(`
                        <li>
                            <div class="prd-img"></div>
                            <div class="prd-cont">
                            <div class="subject">검색 결과가 없습니다</div>
                            </div>
                        </li>
                        `
                    );
                }
            },
            error: function() {
                alert("검색중 문제가 발생했습니다");
                window.location.reload();
            }
        });
        
    });
});

