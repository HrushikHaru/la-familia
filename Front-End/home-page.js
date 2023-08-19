const preloader = document.querySelector(".pre-loader");
const profilePictureElements = document.querySelectorAll(".user-profile-pic");
const profileName = document.querySelector(".handle h3");
const handleName = document.querySelector(".handle p");
const locationFromDB = document.querySelector(".locationFromDB");
const bioFromDB = document.querySelector(".bioFromDB");
const hobbiesFromDB = document.querySelector(".hobbiesFromDB");

preloader.classList.add("show");
setTimeout(() => {
  preloader.classList.remove("show");
}, 1500);

//store userProfileId in localStorage
const getUserProfileInfo = async function () {
  try {
    const response = await fetch(
      `http://localhost:8080/getUserProfile/${localStorage.getItem("userId")}`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        },
      }
    );

    if (response.status === 200) {
      const data = await response.json();

      //setting the userProfieId and displayName as localStorage
      localStorage.setItem("userProfileId", data.userProfileId);
      localStorage.setItem("userDisplayName", data.displayName);

      //setting up profile-name, handle-name, bio, location and hobbies
      profileName.textContent = data.displayName;
      handleName.textContent = `@${data.displayName.toLowerCase()}`;
      locationFromDB.textContent = data.location;
      bioFromDB.textContent = data.bio;
      hobbiesFromDB.textContent = data.hobbies;

      return true;
    }
  } catch (error) {
    console.log(error.message);
  }
};

const getUsersProfileImg = async function () {
  const userDetailsGet = await getUserProfileInfo();

  if (userDetailsGet) {
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

        profilePictureElements.forEach((e) => {
          e.src = imageURL;
        });
      }

      getAllRequests();
    } catch (error) {
      console.log(error.message);
    }
  }
};

getUsersProfileImg();

/*Swiper*/
const swiperWrapper = document.querySelector(".swiper-wrapper");

/*Show list of followers*/
const slider = document.querySelector(".slider");

//Random Number Generator
function getRandomNumbers(n) {
  const shuffledNumbers = Array.from({ length: n }, (_, i) => i + 1);

  for (let i = n - 1; i >= n - 5; i--) {
    const randomIndex = Math.floor(Math.random() * (i + 1));
    if (randomIndex != localStorage.getItem("userProfileId")) {
      [shuffledNumbers[i], shuffledNumbers[randomIndex]] = [
        shuffledNumbers[randomIndex],
        shuffledNumbers[i],
      ];
    }
  }

  return shuffledNumbers.slice(-5);
}

//Total users Count
const getCount = async function () {
  try {
    const usersCount = await fetch("http://localhost:8080/getAllUsersCount", {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      },
    });

    const data = await usersCount.json();
    return data.userCounts;
  } catch (error) {
    console.log(error.message);
  }
};

const getFollowingListToAvoidFollowup = async function () {
  try {
    const response = await fetch(
      `http://localhost:8080/getAllFollowingList/${localStorage.getItem(
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

    data.forEach((e) => {
      set.add(e.followingPersonUserId);
    });

    //when the requests are in request Section but we don't want to show them in follow section
    const response2 = await fetch(
      `http://localhost:8080/getUserProfileRequestsUserProfileId/${localStorage.getItem(
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

    const data2 = await response2.json();

    data2.forEach((e) => {
      set.add(e.userProfileId);
    });

    return set;
  } catch (error) {
    console.log(error.message);
  }
};

const getFollowingList = async function () {
  const count = await getCount();
  //const randomNumbers = getRandomNumbers(count).sort((a, b) => a - b);

  //getting the list of following people to avoid redundancy
  const peopleNotToShowOnFollowCarousel =
    await getFollowingListToAvoidFollowup();

  const response = await fetch(
    `http://localhost:8080/getAllUserProfile/${localStorage.getItem(
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

  data.forEach((e) => {
    if (!peopleNotToShowOnFollowCarousel.has(e.userData.userProfileId)) {
      const imageBytes = Uint8Array.from(atob(e.profilePicture), (c) =>
        c.charCodeAt(0)
      );

      const html = `<div class="slide" data-userId="${
        e.userData.userProfileId
      }">
                <div class="profile-photo followList">
                  <img src="${URL.createObjectURL(new Blob([imageBytes]))}" />
                </div>
                <div class="people-name">${e.userData.userName}</div>
                <div class="handle-name">@${e.userData.userName.toLowerCase()}</div>
                <button class="btn btn-primary followBtn">Follow</button>
              </div>`;

      slider.insertAdjacentHTML("afterbegin", html);
    }
  });

  const followBtn = document.querySelectorAll(".followBtn");
  //const followBtn = document.getElementsByClassName("followBtn");

  //To Follow list carousel
  const slides = document.querySelectorAll(".slide");
  const btnLeft = document.querySelector(".slider__btn--left");
  const btnRight = document.querySelector(".slider__btn--right");

  let currentSlide = 0;
  let maxSlides = slides.length;

  const goToSlide = function (slide) {
    slides.forEach(
      (s, i) => (s.style.transform = `translateX(${100 * (i - slide)}%)`)
    );
  };

  //0%,100%,200%,300%
  btnRight.addEventListener("click", function () {
    if (currentSlide === maxSlides - 1) {
      currentSlide = 0;
    } else {
      currentSlide++;
    }

    goToSlide(currentSlide);
  });

  btnLeft.addEventListener("click", function () {
    if (currentSlide === 0) {
      currentSlide = maxSlides - 1;
    } else {
      currentSlide--;
    }

    goToSlide(currentSlide);
  });

  goToSlide(currentSlide);

  for (const btn of followBtn) {
    btn.addEventListener("click", function () {
      const parentElement = this.parentNode;

      const followBtnUser = parentElement.dataset.userid;

      const requestToDisplayName =
        parentElement.querySelector(".people-name").textContent;

      const sendFollowRequestToUser = async function () {
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
              requestToId: followBtnUser,
              requestToName: requestToDisplayName,
            }),
          });

          console.log(response.status);
          const data = await response.json();

          if (response.status === 200) {
            showModalAndOverlay(
              `😇 ${data.message}`,
              `Friend Request To ${requestToDisplayName} has been sent!`,
              "off",
              "off"
            );

            setTimeout(() => {
              modal.classList.add("hidden");
              overlay.classList.add("hidden");
            }, 2000);

            setTimeout(() => {
              location.reload();
              parentElement.remove();
            }, 3000);
          } else {
            showModalAndOverlay(
              `😥 ${data.message}`,
              `Friend Request To ${requestToDisplayName} wasn't been sent!`,
              "off",
              "off"
            );
            setTimeout(() => {
              modal.classList.add("hidden");
              overlay.classList.add("hidden");
            }, 2000);
          }
        } catch (error) {
          console.log(error.message);
        }
      };

      sendFollowRequestToUser();
    });
  }
};

getFollowingList();

/*Modal Details*/
export const modal = document.querySelector(".modal");
export const overlay = document.querySelector(".overlay");
export const validation = document.querySelector(".validation");
export const errorMessage = document.querySelector(".validation-desc");

export function showModalAndOverlay(validationMsg, validationDesc) {
  validation.textContent = "";
  errorMessage.textContent = "";

  validation.textContent = validationMsg;
  errorMessage.textContent = validationDesc;

  modal.classList.remove("hidden");
  overlay.classList.remove("hidden");
}

/*Request Section*/
const friendRequestContainer = document.querySelector(".friend-requests");

//If you're wondering where this function is called, it's after getting the userProfile Id
async function getAllRequests() {
  try {
    const response = await fetch(
      `http://localhost:8080/showUserProfileRequests/${localStorage.getItem(
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

    data.forEach((request) => {
      const imageBytes = Uint8Array.from(
        atob(request.base64ProfilePicture),
        (c) => c.charCodeAt(0)
      );

      const html = `<div class="request" data-reqId=${request.requestById}>
    <div class="info">
      <div class="profile-photo">
        <img
          src="${URL.createObjectURL(new Blob([imageBytes]))}"
          alt="profile-pic"
        />
      </div>
      <div>
        <h5>${request.requestByName}</h5>
      </div>
    </div>
    <div class="action">
      <button class="btn btn-primary accept-btn">Accept</button>
      <button class="btn decline-btn">Decline</button>
    </div>
  </div>`;

      friendRequestContainer.insertAdjacentHTML("beforeend", html);
    });

    //accept the friend request
    const requestAcceptBtns = document.querySelectorAll(".accept-btn");
    const requestHeader = document.querySelector(".requests");

    if (requestAcceptBtns.length === 0) {
      requestHeader.textContent = "";
    }

    requestAcceptBtns.forEach((acceptRequest) => {
      acceptRequest.addEventListener("click", function () {
        const parentElement = this.parentNode.parentNode;

        const requestByUserId = parentElement.dataset.reqid;

        const requestByUserName = parentElement.querySelector("h5").textContent;

        const acceptRequest = async function () {
          try {
            const response = await fetch(
              `http://localhost:8080/deleteUserProfileRequestsAccept/${localStorage.getItem(
                "userProfileId"
              )}/${requestByUserId}`,
              {
                method: "DELETE",
                headers: {
                  "Content-type": "application/json",
                  Authorization: `Bearer ${localStorage.getItem("userToken")}`,
                },
              }
            );

            if (response.status === 200) {
              showModalAndOverlay(
                "😇 The request has been accepted!",
                `The person named ${requestByUserName} has been added to your following list!`
              );

              parentElement.remove();

              if (requestAcceptBtns.length === 1) {
                console.log(requestAcceptBtns);
                requestHeader.textContent = "";
              }

              setTimeout(() => {
                modal.classList.add("hidden");
                overlay.classList.add("hidden");
              }, 2000);
            } else {
              showModalAndOverlay(
                "😥 The request wasn't processed!",
                `The request wasn't processed. Please try again after some time.`
              );

              setTimeout(() => {
                modal.classList.add("hidden");
                overlay.classList.add("hidden");
              }, 2000);
            }
          } catch (error) {
            console.log(error.message);
          }
        };

        acceptRequest();
      });
    });

    //declining the friend request
    const requestDeclineBtns = document.querySelectorAll(".decline-btn");

    requestDeclineBtns.forEach((declineRequest) => {
      declineRequest.addEventListener("click", function () {
        const parentElement = this.parentNode.parentNode;

        const requestByUserId = parentElement.dataset.reqid;

        const requestByUserName = parentElement.querySelector("h5").textContent;

        const rejectRequest = async function () {
          const response = await fetch(
            `http://localhost:8080/deleteUserProfileRequestsReject/${localStorage.getItem(
              "userProfileId"
            )}/${requestByUserId}`,
            {
              method: "DELETE",
              headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("userToken")}`,
              },
            }
          );

          if (response.status === 200) {
            showModalAndOverlay(
              "😇 The request has been processed successfully",
              `The request with user name ${requestByUserName} has been declined successfully`
            );

            parentElement.remove();

            if (requestAcceptBtns.length === 1) {
              requestHeader.textContent = "";
            }

            setTimeout(() => {
              modal.classList.add("hidden");
              overlay.classList.add("hidden");
            }, 2000);
          } else {
            showModalAndOverlay(
              "😥 The request wasn't processed!",
              `The request wasn't processed. Please try again after some time.`
            );

            setTimeout(() => {
              modal.classList.add("hidden");
              overlay.classList.add("hidden");
            }, 2000);
          }
        };

        rejectRequest();
      });
    });
  } catch (error) {
    console.log(error.message);
  }
}

/*Hamburger menu for right*/
const btnMenu = document.querySelector(".btn-menu");
const requestSlider = document.querySelector(".request-slider");

btnMenu.addEventListener("click", function () {
  if (requestSlider.style.display === "none") {
    requestSlider.style.display = "block";
    btnMenu.innerHTML = "";
    btnMenu.innerHTML = `<i class="uil uil-times"></i>`;
  } else {
    requestSlider.style.display = "none";
    btnMenu.innerHTML = "";
    btnMenu.innerHTML = `<i class="uil uil-bars"></i>`;
  }
});

/*Left side visibility for mobiles and tablets*/
const userBio = document.querySelector(".user-bio");
const userBioShow = document.querySelector(".user-bio-show");

userBioShow.addEventListener("click", function () {
  if (userBio.style.display === "none") {
    userBio.style.display = "block";
  } else {
    userBio.style.display = "none";
  }
});
