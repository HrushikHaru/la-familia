import {
  overlay,
  showModalAndOverlay,
  modal,
  validation,
  errorMessage,
} from "./home-page.js";

const fileUpload = document.querySelector("#file-upload");
const addImageForPostContainer = document.querySelector(".post-img-container");
const addImageForPost = document.querySelector(".create-img-post");
const addItems = document.querySelector(".add-items");
const userDetailsContent = document.querySelector(".user-details-content"); //This is also a form to post
const postCloseBtn = document.querySelector(".post-closeBtn");
const createPost = document.querySelector(".create-post");
const createPostBtn = document.querySelectorAll(".create-post-btn");
const nameOfUserOnPost = document.querySelector(".name-of-user");
const captionTextArea = document.querySelector(".caption-text-area");
const captionErrorMsg = document.querySelector(".error-caption-msg");
const userProfilePicToLogOut = document.querySelector(".top");
const options = document.getElementById("profileOptions");
const logOutLink = document.querySelector(".logout-link");

//Logout functionality
userProfilePicToLogOut.addEventListener("click", function () {
  if (options.style.display === "block") {
    options.style.display = "none";
  } else {
    options.style.display = "block";
  }
});

document.addEventListener("click", function (event) {
  const target = event.target;
  if (target !== userProfilePicToLogOut && target !== options) {
    options.style.display = "none";
  }
});

logOutLink.addEventListener("click", function () {
  localStorage.removeItem("userToken");
  localStorage.removeItem("userFirstName");
  localStorage.removeItem("userProfileId");
  localStorage.removeItem("userId");
  localStorage.removeItem("userDisplayName");
});

/*Create Post functionality*/
createPostBtn.forEach((postBtn) => {
  postBtn.addEventListener("click", function () {
    createPost.classList.remove("hidden");
    overlay.classList.remove("hidden");
  });
});

function closePostContents() {
  createPost.classList.add("hidden");
  overlay.classList.add("hidden");
  addItems.style.display = "block";
  userDetailsContent.style.display = "none";
  addImageForPostContainer.classList.add("hidden");
}

postCloseBtn.addEventListener("click", function () {
  closePostContents();
});

fileUpload.addEventListener("change", function () {
  const file = this.files[0];

  if (file) {
    const reader = new FileReader();

    nameOfUserOnPost.textContent = localStorage.getItem("userDisplayName");

    addItems.style.display = "none";
    userDetailsContent.style.display = "block";
    reader.onload = function () {
      addImageForPost.src = reader.result;
    };
    reader.readAsDataURL(file);

    addImageForPostContainer.classList.remove("hidden");

    const formdata = new FormData();
    formdata.append("file", file);
    formdata.append("userProfileId", localStorage.getItem("userProfileId"));

    userDetailsContent.addEventListener("submit", function (e) {
      e.preventDefault();

      const postImageInFileSystem = async function () {
        try {
          const response = await fetch(
            `http://localhost:8080/addPostImageInFile`,
            {
              method: "POST",
              headers: {
                Authorization: `Bearer ${localStorage.getItem("userToken")}`,
              },
              body: formdata,
            }
          );

          const data = await response.json();

          return data;
        } catch (error) {
          console.log(error.message);
        }
      };

      const createPost = async function () {
        if (captionTextArea.value) {
          const imgSaveData = await postImageInFileSystem();

          const postPicName = imgSaveData.postPicName;

          const td = new Date().toISOString().split("T")[0];
          const t = new Date().toTimeString().split(" ")[0];

          const currentDateTime = td + " " + t;

          if (postPicName) {
            try {
              const response = await fetch(`http://localhost:8080/createPost`, {
                method: "POST",
                headers: {
                  "Content-type": "application/json",
                  Authorization: `Bearer ${localStorage.getItem("userToken")}`,
                },
                body: JSON.stringify({
                  postPicName: postPicName,
                  caption: captionTextArea.value,
                  postDateTime: currentDateTime,
                  userProfileId: localStorage.getItem("userProfileId"),
                }),
              });

              if (response.status === 201) {
                closePostContents();

                showModalAndOverlay(
                  "😇 The post has been posted!",
                  `The post was added at ${currentDateTime}`
                );

                setTimeout(() => {
                  modal.classList.add("hidden");
                  overlay.classList.add("hidden");
                }, 2000);

                // setTimeout(() => {
                //   location.reload();
                // }, 3000);
              } else {
                closePostContents();

                showModalAndOverlay(
                  "😥 The request wasn't processed!",
                  `The request wasn't processed. Please try again after some time.`
                );

                setTimeout(() => {
                  modal.classList.add("hidden");
                  overlay.classList.add("hidden");
                }, 2000);

                // setTimeout(() => {
                //   location.reload();
                // }, 3000);
              }
            } catch (error) {
              console.log(error.message);
            }
          }
        } else {
          captionErrorMsg.style.display = "block";
        }
      };

      createPost();
    });
  }
});

/*Add posts for user*/
const feeds = document.querySelector(".feeds");

const addPostsOnFeeds = async function () {
  try {
    const response = await fetch(
      `http://localhost:8080/getPostsFromFollowers/${localStorage.getItem(
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
            <img src="${URL.createObjectURL(new Blob([profileImageBytes]))}" />
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

      feeds.insertAdjacentHTML("beforeend", html);
    });
  } catch (error) {
    console.log(error.message);
  }
};

setTimeout(() => {
  addPostsOnFeeds();
}, 1000);

///////////////////////////
///////////////////////////
///////////////////////////
/////////////////////////// The Code Below is Based on Updation
///////////////////////////
///////////////////////////
///////////////////////////
/*Example of Long Polling*/

// const updateFeeds = async function (totalPosts) {
//   try {
//     const response = await fetch(
//       `http://localhost:8080/updateFeeds/${localStorage.getItem(
//         "userProfileId"
//       )}/${totalPosts}`,
//       {
//         method: "GET",
//         headers: {
//           "Content-type": "application/json",
//           Authorization: `Bearer ${localStorage.getItem("userToken")}`,
//         },
//       }
//     );

//     const data = await response.json();

//     data.forEach((post) => {
//       const profileImageBytes = Uint8Array.from(
//         atob(post.base64EncodingProfilePic),
//         (c) => c.charCodeAt(0)
//       );

//       const postImageBytes = Uint8Array.from(
//         atob(post.base64EncodingPostPic),
//         (c) => c.charCodeAt(0)
//       );

//       const html = `<div class="feed" data-postId = "${post.postId}">
//       <div class="head">
//         <div class="user">
//           <div class="profile-photo">
//             <img src="${URL.createObjectURL(new Blob([profileImageBytes]))}" />
//           </div>
//           <div class="ingo">
//             <h3>${post.userProfileDisplayName}</h3>
//             <small> ${post.location},${post.postdateTime.substring(
//         0,
//         post.postdateTime.length - 2
//       )}</small>
//           </div>
//         </div>
//       </div>
//       <div class="photo">
//         <img
//           src="${URL.createObjectURL(new Blob([postImageBytes]))}"
//           alt="image"
//         />
//       </div>

//       <div class="action-button">
//         <div class="interaction-buttons">
//           <span class="like-post"><i class="fa-solid fa-heart"></i
//           ></span>
//           <span><i class="uil uil-comment comments-for-post"></i></span>
//         </div>
//         <div class="bokmark">
//           <i class="uil uil-bookmark"></i>
//         </div>
//       </div>
//       <div class="liked-by">
//         <span>
//           <img
//             src="user-profiles-pics/18/profile-pic-18.jpg"
//             alt="profile-pic"
//           />
//         </span>
//         <span>
//           <img
//             src="user-profiles-pics/18/profile-pic-18.jpg"
//             alt="profile-pic"
//           />
//         </span>
//         <span>
//           <img
//             src="user-profiles-pics/18/profile-pic-18.jpg"
//             alt="profile-pic"
//           />
//         </span>
//         <p class="liked-by-p">Liked by <b>Hello</b> and 2 others</p>
//       </div>

//       <div class="caption">
//         <p><b>${post.userProfileDisplayName} </b>${post.caption}</p>
//       </div>
//       <div class="comments">View all 200 comments</div>
//     </div>`;

//       feeds.insertAdjacentHTML("beforeend", html);
//     });
//     console.log(data);
//     console.log("updated");
//   } catch (error) {
//     console.log(error.message);
//   }
// };

// const getUpdatesOnPosts = async function () {
//   const totalPosts = document.querySelectorAll(".feed").length;

//   console.log(totalPosts);

//   try {
//     const response = await fetch(
//       `http://localhost:8080/long-polling/update-feeds/${localStorage.getItem(
//         "userProfileId"
//       )}/${totalPosts}`,
//       {
//         method: "GET",
//         headers: {
//           "Content-type": "application/json",
//           Authorization: `Bearer ${localStorage.getItem("userToken")}`,
//         },
//       }
//     );

//     //console.log(response);
//     const data = await response.json();

//     if (data.message == "false") {
//       updateFeeds(totalPosts);
//     }

//     await getUpdatesOnPosts();
//   } catch (error) {
//     console.log(error.message);
//   }
// };

// setTimeout(() => {
//   getUpdatesOnPosts();
// }, 2000);

//Server Side Events

// const clientId = localStorage.getItem("userProfileId"); // Replace with the client's actual ID
// const eventSource = new EventSource(
//   `http://localhost:8080/updates/feed-updates/${clientId}`
// );

// eventSource.onmessage = (event) => {
//   const update = JSON.parse(event.data);
//   // Process the received update
//   console.log(update);
// };
