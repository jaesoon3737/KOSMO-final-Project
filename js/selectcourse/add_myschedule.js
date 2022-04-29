$(document).ready(function() {
    $('#add_myschedule').on('click', function() {
        const email = $('#member_email').val();
        const cnum = $('#course_cnum').val();
        const cname = $('#course_cname').val();
        const cintro = $('#course_cintro').val();

        let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
        let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
        let csrfToken = $('meta[name="_csrf"]').attr('content');
        $.ajax({
            url: "/jejufriends/select_course/wishList.json",
            type: "POST",
            data: {email: email, cnum: cnum, cname: cname, cintro: cintro},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(wishList) {
                if(wishList.exist) {
                    alert("나의 일정에 추가 완료");
                }else {
                    alert("이미 존재하는 일정입니다");
                }
            }
        });    
    });
});