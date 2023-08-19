import { overlay } from "./home-page.js";

const button1 = document.getElementById("button1");
const button2 = document.getElementById("button2");
const personalizedFeeds = document.querySelector(".feeds");
const publicFeeds = document.querySelector(".public-feeds");

button1.addEventListener("click", function () {
  button1.style.backgroundColor = "black";
  button1.style.color = "white";
  button2.style.backgroundColor = "white";
  button2.style.color = "black";

  //Show the personalized Feeds
  personalizedFeeds.classList.remove("hidden");

  //Hide the public Feeds
  publicFeeds.classList.add("hidden");
});

button2.addEventListener("click", function () {
  button2.style.backgroundColor = "black";
  button2.style.color = "white";
  button1.style.backgroundColor = "white";
  button1.style.color = "black";

  //Hide the personalized Feeds
  personalizedFeeds.classList.add("hidden");

  //Show the public Feeds
  publicFeeds.classList.remove("hidden");
});

const getAllThePosts = async function () {
  try {
    const response = await fetch(`http://localhost:8080/getAllThePosts`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
    });

    const data = await response.json();

    data.forEach((post) => {
      let person = "person";
      if (post.likeCount == 0) {
        person = "";
      }

      if (post.likeCount > 1) {
        person = "people";
      }

      const dateTime = post.postdateTime.substring(
        2,
        post.postdateTime.length - 2
      );

      //Changing into dateTime Format
      const dateString = dateTime;
      const year = parseInt(dateString.substring(0, 2)) + 2000;
      const month = parseInt(dateString.substring(3, 5)) - 1;
      const day = parseInt(dateString.substring(6, 8));
      const hours = parseInt(dateString.substring(9, 11));
      const minutes = parseInt(dateString.substring(12, 14));
      const seconds = parseInt(dateString.substring(15, 17));

      const date = new Date(year, month, day, hours, minutes, seconds);

      const diff = Date.now() - date;

      const secondsShow = Math.floor(diff / 1000);
      const minutesShow = Math.floor(secondsShow / 60);
      const hoursShow = Math.floor(minutesShow / 60);
      const daysShow = Math.floor(hoursShow / 24);
      const weeksShow = Math.floor(daysShow / 7);

      let showAsDateTime;
      if (weeksShow > 0) {
        showAsDateTime =
          weeksShow + " week" + (weeksShow === 1 ? "" : "s") + " ago";
      } else if (daysShow > 0) {
        showAsDateTime =
          daysShow + " day" + (daysShow === 1 ? "" : "s") + " ago";
      } else if (hoursShow > 0) {
        showAsDateTime =
          hoursShow + " hour" + (hoursShow === 1 ? "" : "s") + " ago";
      } else if (minutesShow > 0) {
        showAsDateTime =
          minutesShow + " minute" + (minutesShow === 1 ? "" : "s") + " ago";
      } else {
        showAsDateTime =
          secondsShow + " second" + (secondsShow === 1 ? "" : "s") + " ago";
      }

      const profileImageBytes = Uint8Array.from(
        atob(post.base64EncodingProfilePic),
        (c) => c.charCodeAt(0)
      );

      const postImageBytes = Uint8Array.from(
        atob(post.base64EncodingPostPic),
        (c) => c.charCodeAt(0)
      );

      const html = `<div class="feed" data-postId = "${post.postId}">
        <div class="head">
          <a href = "profile-details.html?userProfileId=${
            post.userProfileId
          }" class="postByFollowing"><div class="user">
            <div class="profile-photo">
              <img src="${URL.createObjectURL(
                new Blob([profileImageBytes])
              )}" />
            </div>
            <div class="ingo">
              <h3>${post.userProfileDisplayName}</h3>
              <small>${post.location}</small>
              <small class="dateTimeShow">${showAsDateTime}</small>
            </div>
          </div>
        </div></a>
        <div class="photo">
          <img
            loading = "lazy"
            src="${URL.createObjectURL(new Blob([postImageBytes]))}"
            alt="image"
          />
        </div>
  
        <div class="action-button">
          <div class="interaction-buttons">
            <span class="like-post"><i class="fa-solid fa-heart"></i
            ></span>
            <span><i class="uil uil-comment comments-for-post"></i></span>
          </div>
          <div class="bokmark">
            <i class="uil uil-bookmark"></i>
          </div>
        </div>
        <div class="liked-by">
          <p class="liked-by-p">Liked by <b class="like-count">${
            post.likeCount
          }</b> ${person}</p>
        </div>
  
        <div class="caption">
          <p><b>${post.userProfileDisplayName} </b>${post.caption}</p>
        </div>
        <div class="comments">View all <b class="comment-count">${
          post.commentCount
        }</b> comments</div>
      </div>`;

      publicFeeds.insertAdjacentHTML("beforeend", html);
    });

    //For comments Section
    const commentBtns = document.querySelectorAll(".comments-for-post");
    commentsForPost(data, commentBtns);
  } catch (error) {
    console.log(error.message);
  }

  const likePosts = document.querySelectorAll(".like-post");

  userLikedPosts(likePosts);

  likePosts.forEach((likeButton) => {
    likeButton.addEventListener("click", function () {
      const postId = this.parentNode.parentNode.parentNode.dataset.postid;

      const likeCountNode = this.parentNode.parentNode.nextElementSibling;

      const likeCountElement = likeCountNode.querySelector(".like-count");

      if (!this.classList.contains("change-red")) {
        this.classList.add("change-red");

        likePost(postId);
        likeCountElement.textContent = Number(likeCountElement.textContent) + 1;
      } else {
        this.classList.remove("change-red");

        dislikePost(postId);
        likeCountElement.textContent = +likeCountElement.textContent - 1;
      }
    });
  });
};

/*This one should run after the personalized feed*/
setTimeout(() => {
  getAllThePosts();
}, 2000);

async function userLikedPosts(likedPosts) {
  const response = await fetch(
    `http://localhost:8080/getLikesOnPost/${localStorage.getItem(
      "userProfileId"
    )}`,
    {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
    }
  );

  const data = await response.json();

  const set = new Set();

  data.forEach((postLiked) => {
    set.add(postLiked.postId);
  });

  likedPosts.forEach((post) => {
    const postId = post.parentNode.parentNode.parentNode.dataset.postid;

    if (set.has(Number(postId))) {
      post.classList.add("change-red");
    }
  });
}

async function likePost(postId) {
  const response = await fetch(`http://localhost:8080/addLikeToThePost`, {
    method: "POST",
    headers: {
      "Content-type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    },
    body: JSON.stringify({
      postId: postId,
      userProfileId: localStorage.getItem("userProfileId"),
      userDisplayName: localStorage.getItem("userDisplayName"),
    }),
  });

  const data = await response.json();

  console.log(data);
}

async function dislikePost(postId) {
  const response = await fetch(
    `http://localhost:8080/deleteLikeOnThePost/${postId}/${localStorage.getItem(
      "userProfileId"
    )}`,
    {
      method: "DELETE",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
    }
  );

  const data = await response.json();

  console.log(data);
}

/*Comments Section*/
const commentCloseBtn = document.querySelector(".comment-closeBtn");
const commentsModal = document.querySelector(".add-comments");
const postProfilePic = document.querySelector(".post-profile-pic");
const userNameOfPostProfilePic = document.querySelector(".name-of-user-post");
const picOfPost = document.querySelector(".create-img-post-for-comment");
const captionBy = document.querySelector(".caption-by");
const postCaption = document.querySelector(".caption-from-post");
const postCommentBtn = document.querySelector(".postComment");
const commentInput = document.querySelector(".comment-input");
const commentsContainer = document.querySelector(".comments-by-users");
const eachComments = document.getElementsByClassName("each-comment");

const commentsForPost = async function (data, commentBtns) {
  const dataComments = await data;

  const map = new Map();
  dataComments.forEach((comment, i, _) => {
    map.set(comment.postId, i);
  });

  commentBtns.forEach((commentBtn) => {
    commentBtn.addEventListener("click", function () {
      const postId =
        this.parentNode.parentNode.parentNode.parentNode.dataset.postid;

      const commentNode =
        this.parentNode.parentNode.parentNode.nextElementSibling
          .nextElementSibling.nextElementSibling;

      const commentCountElement = commentNode.querySelector(".comment-count");

      if (map.has(+postId)) {
        commentsModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
        const details = dataComments[map.get(+postId)];

        userNameOfPostProfilePic.textContent = details.userProfileDisplayName;
        captionBy.textContent = details.userProfileDisplayName;
        postCaption.textContent = details.caption;

        const profileImageInBytes = Uint8Array.from(
          atob(details.base64EncodingProfilePic),
          (c) => c.charCodeAt(0)
        );
        postProfilePic.src = URL.createObjectURL(
          new Blob([profileImageInBytes])
        );

        const postImageBytes = Uint8Array.from(
          atob(details.base64EncodingPostPic),
          (c) => c.charCodeAt(0)
        );
        picOfPost.src = URL.createObjectURL(new Blob([postImageBytes]));

        getAllCommentsForThePost(postId, commentCountElement);
      }
    });
  });
};

commentInput.addEventListener("keyup", function (e) {
  const value = e.currentTarget.value;

  if (value === "") {
    postCommentBtn.style.color = "lightgray";
  } else {
    postCommentBtn.style.color = "black";
    postCommentBtn.style.cursor = "pointer";
  }
});

const getProfilePicOfUser = async function () {
  try {
    const response = await fetch(
      `http://localhost:8080/getUserProfilePic/${localStorage.getItem(
        "userProfileId"
      )}`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      }
    );

    if (response.status === 200) {
      const data = await response.blob();

      const imageURL = URL.createObjectURL(data);

      return imageURL;
    }
  } catch (error) {
    console.log(error.message);
  }
};

const getAllCommentsForThePost = async function (postId, commentCountElement) {
  //To get the profile-image of current user
  commentsContainer.innerHTML = "";

  try {
    const response = await fetch(
      `http://localhost:8080/getAllComments/${postId}`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      }
    );

    const data = await response.json();

    if (eachComments.length === 0) {
      data.forEach((comment) => {
        const imageBytes = Uint8Array.from(
          atob(comment.base64EncodedprofilePicName),
          (c) => c.charCodeAt(0)
        );

        const html = `<div class="each-comment" data-commentId=${
          comment.comment_id
        }>
          <div class="comment-by-user-pfp">
            <img src="${URL.createObjectURL(new Blob([imageBytes]))}" />
          </div>
          <div class="comment-info">
            <p class="commenter-name">${comment.userDisplayName}</p>
            <p>${comment.comment_caption}</p>
          </div>
        </div>`;

        commentsContainer.insertAdjacentHTML("beforeend", html);
      });
    }
  } catch (error) {
    console.log(error.message);
  }

  const imageURL = await getProfilePicOfUser();

  postCommentBtn.addEventListener("click", function () {
    const html = `<div class="each-comment remove">
              <div class="comment-by-user-pfp">
                <img src="${imageURL}" class="user-profile-pic" />
              </div>
              <div class="comment-info">
                <p class="commenter-name">${localStorage.getItem(
                  "userDisplayName"
                )}</p>
                <p>${commentInput.value}</p>
              </div>
            </div>`;

    postCommentToDB(postId).then((response) => {
      if (response === true) {
        commentInput.value = "";
        commentsContainer.insertAdjacentHTML("beforeend", html);
        commentCountElement.textContent = +commentCountElement.textContent + 1;
      } else {
        showModalAndOverlay(
          "The request wasn't processed",
          "The comment wasn't posted because of an error."
        );

        setTimeout(() => {
          modal.classList.add("hidden");
          overlay.classList.add("hidden");
        }, 3000);
      }
    });
  });
};

const postCommentToDB = async function (postId) {
  try {
    const response = await fetch(`http://localhost:8080/postComment`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
      body: JSON.stringify({
        postId: postId,
        commentCaption: commentInput.value,
        userProfileId: localStorage.getItem("userProfileId"),
        userProfileDisplayName: localStorage.getItem("userDisplayName"),
      }),
    });

    if (response.status === 201) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.log(error.message);
  }
};

commentCloseBtn.addEventListener("click", function () {
  commentsModal.classList.add("hidden");
  overlay.classList.add("hidden");
  const eachCommentsOfPost = [...eachComments];

  eachCommentsOfPost.forEach((comment) => {
    if (comment.classList.contains("remove")) {
      comment.remove();
    }
  });
});
