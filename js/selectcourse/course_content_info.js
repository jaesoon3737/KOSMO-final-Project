$(document).ready(function() {
    $(document).on('click', '.content_info', function() {
        const contentName = $(this).siblings('.content_name').text();
        console.log("contentName: " + contentName);
        window.open(`/jejufriends/select_course/selectcontentInfo.do?contentname=${contentName}`, contentName, 'width=' + screen.width + ',height=' + screen.height + ',fullscreen=yes', false);
    });
});

let option = true;
$(document).ready(function() {
    $(document).on('click', '.course_info', function() {
        const lodgment = $(this).siblings('.lodgment').val();
        if($(`#content_list_${lodgment}`).is(':visible')) { // is(':visible') 은 hide 이면 false 를 반환한다.
            $(`#content_list_${lodgment}`).hide();
            $(this).text(`▶ ${lodgment}박 일정 경유지`);
        }else {
            $(`#content_list_${lodgment}`).show();
            $(this).text(`▼ ${lodgment}박 일정 경유지`);
        }
    });
});