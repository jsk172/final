function startSlideshow() {
  let currentIdx = 0;
  const pictures = document.querySelectorAll(".main_pic");
  const intervalTime = 5000;
  let slideshowInterval;

  pictures[currentIdx].classList.add("active");

  function nextPic() {
    pictures[currentIdx].classList.remove("active");
    currentIdx = (currentIdx + 1) % pictures.length;
    pictures[currentIdx].classList.add("active");
  }

  slideshowInterval = setInterval(nextPic, intervalTime);

  function toggleBtn() {
    let btn = document.querySelector(".main_toggle_btn");
    let timer = document.querySelector(".timer");

    if (timer.style.animationPlayState == "paused" && !slideshowInterval) {
      timer.style.animationPlayState = "running";
      btn.innerHTML = "||";
      animationPaused = false;
      timer.classList.remove("main_new_timer");
      timer.classList.add("main_timer");
      timer = document.querySelector(".main_timer");
      slideshowInterval = setInterval(nextPic, intervalTime);
    } else {
      timer.style.animationPlayState = "paused";
      btn.innerHTML = "▷";
      animationPaused = true;
      timer.classList.remove("main_timer");
      timer.classList.add("main_new_timer");
      timer = document.querySelector(".main_new_timer");

      clearInterval(slideshowInterval);
      slideshowInterval = null;
    }
  }
  document
    .querySelector(".main_toggle_btn")
    .addEventListener("click", toggleBtn);
}

document.addEventListener("DOMContentLoaded", startSlideshow);

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let textBox = document.querySelector(".main_mid_text_box");
  let triggerPosition = 200; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    textBox.classList.add("welcome_box_visible");
    textBox.classList.remove("welcome_box_hidden");
  } else {
    textBox.classList.remove("welcome_box_visible");
    textBox.classList.add("welcome_box_hidden");
  }
});

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let boardBox = document.querySelector(".main_mid_board_box");
  let triggerPosition = 600; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    boardBox.classList.add("board_box_visible");
    boardBox.classList.remove("board_box_hidden");
  } else {
    boardBox.classList.remove("board_box_visible");
    boardBox.classList.add("board_box_hidden");
  }
});

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let icon1 = document.querySelector(".main_use_info");
  let icon2 = document.querySelector(".main_reading_event");
  let icon3 = document.querySelector(".main_study_room");
  let icon4 = document.querySelector(".main_request_book");
  let icon5 = document.querySelector(".main_my_library");
  let triggerPosition = 1100; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    icon1.classList.add("icon1_visible");
    icon1.classList.remove("icon1_hidden");
  } else {
    icon1.classList.remove("icon1_visible");
    icon1.classList.add("icon1_hidden");
  }

  if (scrollPosition > triggerPosition) {
    icon2.classList.add("icon2_visible");
    icon2.classList.remove("icon2_hidden");
  } else {
    icon2.classList.remove("icon2_visible");
    icon2.classList.add("icon2_hidden");
  }

  if (scrollPosition > triggerPosition) {
    icon3.classList.add("icon3_visible");
    icon3.classList.remove("icon3_hidden");
  } else {
    icon3.classList.remove("icon3_visible");
    icon3.classList.add("icon3_hidden");
  }

  if (scrollPosition > triggerPosition) {
    icon4.classList.add("icon4_visible");
    icon4.classList.remove("icon4_hidden");
  } else {
    icon4.classList.remove("icon4_visible");
    icon4.classList.add("icon4_hidden");
  }

  if (scrollPosition > triggerPosition) {
    icon5.classList.add("icon5_visible");
    icon5.classList.remove("icon5_hidden");
  } else {
    icon5.classList.remove("icon5_visible");
    icon5.classList.add("icon5_hidden");
  }
});

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let bookTextBox = document.querySelector(".main_bottom_text_box");
  let triggerPosition = 1600; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    bookTextBox.classList.add("book_text_box_visible");
    bookTextBox.classList.remove("book_text_box_hidden");
  } else {
    bookTextBox.classList.remove("book_text_box_visible");
    bookTextBox.classList.add("book_text_box_hidden");
  }
});

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let book1 = document.querySelector("#book1");
  let book2 = document.querySelector("#book2");
  let book3 = document.querySelector("#book3");
  let book4 = document.querySelector("#book4");
  let book5 = document.querySelector("#book5");
  let triggerPosition = 2000; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    book1.classList.add("book1_visible");
    book1.classList.remove("book1_hidden");
  } else {
    book1.classList.remove("book1_visible");
    book1.classList.add("book1_hidden");
  }

  if (scrollPosition > triggerPosition) {
    book2.classList.add("book2_visible");
    book2.classList.remove("book2_hidden");
  } else {
    book2.classList.remove("book2_visible");
    book2.classList.add("book2_hidden");
  }

  if (scrollPosition > triggerPosition) {
    book3.classList.add("book3_visible");
    book3.classList.remove("book3_hidden");
  } else {
    book3.classList.remove("book3_visible");
    book3.classList.add("book3_hidden");
  }

  if (scrollPosition > triggerPosition) {
    book4.classList.add("book4_visible");
    book4.classList.remove("book4_hidden");
  } else {
    book4.classList.remove("book4_visible");
    book4.classList.add("book4_hidden");
  }

  if (scrollPosition > triggerPosition) {
    book5.classList.add("book5_visible");
    book5.classList.remove("book5_hidden");
  } else {
    book5.classList.remove("book5_visible");
    book5.classList.add("book5_hidden");
  }
});

window.addEventListener("scroll", function () {
  let scrollPosition = window.scrollY;
  let bookTextBox = document.querySelector(".icons_background");
  let triggerPosition = 1100; // 원하는 스크롤 위치

  if (scrollPosition > triggerPosition) {
    bookTextBox.classList.add("icons_background_visible");
    bookTextBox.classList.remove("icons_background_hidden");
  } else {
    bookTextBox.classList.remove("icons_background_visible");
    bookTextBox.classList.add("icons_background_hidden");
  }
});
