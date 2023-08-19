const editProfile = document.querySelector(".edit-profile");
const followBtn = document.querySelector("#followBtn");
const profilePictureElements = document.querySelector(".user-profile-pic");
const profilePictureStable = document.querySelector(".user-profile-pic-stable");
const preloader = document.querySelector(".pre-loader");

const userName = document.querySelector(".user-name");
const handleName = document.querySelector(".handle-name");
const locationUser = document.querySelector(".location-user");
const hobbies = document.querySelector(".hobbies-user");
const bio = document.querySelector(".bio-user");
const followers = document.querySelector(".followers-count");
const following = document.querySelector(".following-count");
const posts = document.querySelector(".post-count");
const followersCount = document.querySelector(".followers-count");
const options = document.getElementById("profileOptions");
const userPosts = document.querySelector(".users-posts");
const logOutLink = document.querySelector(".logout-link");

preloader.classList.add("show");
setTimeout(() => {
  preloader.classList.remove("show");
}, 1500);

/*To Log out the user*/
profilePictureStable.addEventListener("click", function () {
  if (options.style.display === "block") {
    options.style.display = "none";
  } else {
    options.style.display = "block";
  }
});

document.addEventListener("click", function (event) {
  const target = event.target;
  if (target !== profilePictureStable && target !== options) {
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

/*To get the info about the profile in consideration*/
const url = new URL(location.href);
let userId;

if (url.searchParams.size == 0) {
  followBtn.style.display = "none";
  userId = localStorage.getItem("userProfileId");
} else {
  editProfile.style.display = "none";
  userId = url.searchParams.get("userProfileId");
  console.log(userId);

  /*To Check Whether this user follows the other user*/
  const checkIfFollows = async function () {
    try {
      const response = await fetch(
        `http://localhost:8080/checkIfUserFollowsTheOtherUser/${localStorage.getItem(
          "userProfileId"
        )}/${userId}`,
        {
          method: "GET",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
        }
      );

      const data = await response.json();

      const follows = data.follows;

      if (follows) {
        followBtn.textContent = "";
        followBtn.textContent = "Unfollow";
      }
    } catch (error) {
      console.log(error.message);
    }
  };

  checkIfFollows();
}

const getStableUsersProfileImg = async function () {
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

      profilePictureStable.src = imageURL;
    }
  } catch (error) {
    console.log(error.message);
  }
};

getStableUsersProfileImg();

const getUsersProfileImg = async function (userId) {
  try {
    const response = await fetch(
      `http://localhost:8080/getUserProfilePic/${userId}`,
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

      profilePictureElements.src = imageURL;
    }
  } catch (error) {
    console.log(error.message);
  }
};

getUsersProfileImg(userId);

const getDetailsOfUser = async function (userId) {
  try {
    const response = await fetch(
      `http://localhost:8080/getAllDetailsOfUser/${userId}`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      }
    );

    const data = await response.json();

    userName.textContent = data.displayName;
    handleName.textContent = `@${data.displayName.toLowerCase()}`;
    locationUser.textContent = data.location;
    hobbies.textContent = data.hobbies;
    bio.textContent = data.bio;
    followers.textContent = data.followers;
    following.textContent = data.following;
    posts.textContent = data.posts;
  } catch (error) {
    console.log(error.message);
  }
};

getDetailsOfUser(userId);

const getPostsDetails = async function (userId) {
  try {
    const response = await fetch(
      `http://localhost:8080/getAllThePostsOfTheUser/${userId}`,
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
      const imgInBytes = Uint8Array.from(atob(post.base64EncodedPostPic), (c) =>
        c.charCodeAt(0)
      );

      const html = `<div class="each-post">
    <img src="${URL.createObjectURL(
      new Blob([imgInBytes])
    )}" class="post-image" alt="Image of Post" />
    <div class="post-data hidden">
      <div class="metrics">
        <span
          ><i class="uil uil-comment-dots"></i><span>${
            post.commentCount
          }</span></span
        >
        <span><i class="uil uil-heart"></i><span>${post.likeCount}</span></span>
      </div>
      <p class="caption">
       ${post.caption}
      </p>
    </div>
  </div>`;

      userPosts.insertAdjacentHTML("beforeend", html);
    });
  } catch (error) {
    console.log(error.message);
  }

  userPosts.addEventListener("mouseover", function (e) {
    if (e.target.classList.contains("post-image")) {
      const imgContainer = e.target.closest("div");
      const img = e.target.closest("img");
      const postData = e.target.closest("div").querySelector(".post-data");

      imgContainer.style.backgroundColor = "black";
      img.style.opacity = 0.4;
      postData.classList.remove("hidden");
      imgContainer.style.transform = "scale(105%)";
      postData.style.opacity = 1;
    }
  });

  userPosts.addEventListener("mouseout", function (e) {
    if (e.target.classList.contains("post-image")) {
      const imgContainer = e.target.closest("div");
      const img = e.target.closest("img");
      const postData = e.target.closest("div").querySelector(".post-data");

      imgContainer.style.backgroundColor = "";
      img.style.opacity = 1;
      postData.classList.add("hidden");
      imgContainer.style.transform = "scale(100%)";
      postData.style.opacity = 0;
    }
  });
};

getPostsDetails(userId);

/*To follow and unfollow the other user*/
followBtn.addEventListener("click", function () {
  if (followBtn.textContent === "Unfollow") {
    const unfollowUser = async function () {
      try {
        const response = await fetch(
          `http://localhost:8080/unfollowTheOtherUserFromList/${localStorage.getItem(
            "userProfileId"
          )}/${userId}`,
          {
            method: "DELETE",
            headers: {
              "Content-type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("userToken")}`,
            },
          }
        );

        if (response.status === 200) {
          const data = await response.json();

          followBtn.textContent = "";
          followBtn.textContent = "Follow";
          followersCount.textContent = +followersCount.textContent - 1;
        }
      } catch (error) {
        console.log(error.message);
      }
    };

    unfollowUser();
  } else {
    const followOtherUser = async function () {
      const requestToDisplayName = document.querySelector(".user-name");

      try {
        const response = await fetch("http://localhost:8080/sendRequestTo", {
          method: "POST",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
          body: JSON.stringify({
            requestById: `${localStorage.getItem("userProfileId")}`,
            requestByName: `${localStorage.getItem("userDisplayName")}`,
            requestToId: userId,
            requestToName: requestToDisplayName.textContent,
          }),
        });

        if (response.status === 200) {
          const data = await response.json();

          followBtn.textContent = "";
          followBtn.textContent = "Unfollow";
          followersCount.textContent = +followersCount.textContent + 1;
        }
      } catch (error) {
        console.log(error.message);
      }
    };

    followOtherUser();
  }
});

/*Editing Profile*/
editProfile.addEventListener("click", function () {
  console.log("Hello");
});
