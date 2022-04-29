
let selectPage = 1;
let maxPage = 0;
let wishlistListTemp = [];
function wishListPageSet(pageNum, page, wishlistList) {
    $('#paging').empty();
    let pageHtml = '';
    let startPage = 1;
    let loop = 0;
    // wishlistList 가 배열이면 새로 전역변수에 대입
    if(Array.isArray(wishlistList)) wishlistListTemp = wishlistList;

    if(isNaN(pageNum)) {
        alert("숫자 아님");
    }

    // 마지막 페이지 계산
    maxPage = Math.floor(wishlistListTemp.length / pageNum);
    if(wishlistListTemp.length%pageNum != 0) maxPage++;
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
    let wishlistHtml = '';
    let wishlistListStart = page*pageNum-pageNum;
    let wishlistListLast = page*pageNum;

    $("#wishlist_list").empty();
    if(wishlistListTemp.length != 0) {
        for(let i = wishlistListStart; i < wishlistListLast; i++) {
            if(wishlistListTemp[i] != null){
                let wishlist = wishlistListTemp[i];
                wishlistHtml += `
                    <tr class="xans-record-">
                        <td class="left" style="width:35%;padding-left:15px;">
                            <strong class="name"><a href="/jejufriends/select_course/courseContent.do?cnum=${wishlist.cnum}" class="ec-product-name">${wishlist.cname}</a></strong>
                            <ul class="xans-element- xans-myshop xans-myshop-optionall option">
                                <li class="xans-record-">
                                    <strong class="displaynone"></strong> 
                                </li>
                            </ul>
                        </td>
                        <td class="left" style="width:45%;">
                            <strong class="name"><a href="/jejufriends/select_course/courseContent.do?cnum=${wishlist.cnum}" class="ec-product-name">${wishlist.cintro}</a></strong>
                            <ul class="xans-element- xans-myshop xans-myshop-optionall option">
                                <li class="xans-record-">
                                    <strong class="displaynone"></strong> 
                                </li>
                            </ul>
                        </td>
                        <td class="button" style="width:20%;">
                            <input class="delete_one" type="hidden" value="${wishlist.winum}"/>
                            <a class="btnNormal btn_wishlist_del wishlist_delete" rel="9||000A||" style="cursor:pointer;"><i class="icoDelete"></i> 삭제</a>
                        </td>
                    </tr>
                `;
            }
        }
    }else {
        wishlistHtml += `
            <tr style="background-color:#FFFFFF; color:#555555;" class="">
                <td colspan="7" class="subject" style="text-align: center;">찜 목록이 없습니다</td>
            </tr>
        `;
    }
    $("#wishlist_list").append(wishlistHtml);

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
        wishListPageSet(pageNum, page, null);
    });

    // 다음페이지 버튼 누를 때 이벤트
    $(document).on('click', '.nextPage', function() {
        const page = selectPage + 1;
        const pageNum = $('#pageNum option:selected').val();
        wishListPageSet(pageNum, page, null);
    });

    // 이전페이지 버튼 누를 때 이벤트
    $(document).on('click', '.beforePage', function() {
        const page = selectPage - 1;
        const pageNum = $('#pageNum option:selected').val();
        wishListPageSet(pageNum, page, null);
    });

    // 페이지 수 변경할 때 이벤트
    $(document).on('change', '#pageNum', function() {
        const page = selectPage;
        const pageNum = $('#pageNum option:selected').val();
        pageSet(pageNum, page, null);
    });
});
