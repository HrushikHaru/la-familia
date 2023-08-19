const preloader = document.querySelector(".pre-loader");
const profileImage = document.getElementById("profile-image");
const profileImageUpload = document.getElementById("profile-image-upload");
const userProfileForm = document.querySelector("form");
const displayName = document.querySelector(".displayName");
const bio = document.querySelector(".bio");
const locationOfUser = document.querySelector(".location");
const hobbies = document.querySelector(".hobbies");

preloader.classList.add("show");
setTimeout(() => {
  preloader.classList.remove("show");
}, 1500);

const getAllDetailsOfUser = async function () {
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

    const data = await response.blob();

    profileImage.src = URL.createObjectURL(data);
  } catch (error) {
    console.log(error.message);
  }
};

const getAllDetails = async function () {
  try {
    await getAllDetailsOfUser();

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

    const data = await response.json();

    displayName.value = data.displayName;
    bio.value = data.bio;
    locationOfUser.value = data.location;
    hobbies.value = data.hobbies;
  } catch (error) {
    console.log(error.message);
  }
};

getAllDetails();

profileImageUpload.addEventListener("change", function () {
  const file = this.files[0];
  if (file) {
    const reader = new FileReader();

    reader.onload = function () {
      profileImage.src = reader.result;
    };
    reader.readAsDataURL(file);

    console.log(file);
  }
});

/*Handling Updated Info*/
userProfileForm.addEventListener("submit", function (e) {
  e.preventDefault();

  if (profileImageUpload.files.length === 0) {
    console.log("No change");
    try {
      const updateWithoutPic = async function () {
        const response = await fetch(`http://localhost:8080/updateWithouPic`, {
          method: "PUT",
          headers: {
            "Content-type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
          body: JSON.stringify({
            displayName: displayName.value,
            bio: bio.value,
            location: locationOfUser.value,
            hobbies: hobbies.value,
            userProfileId: localStorage.getItem("userProfileId"),
          }),
        });

        if (response.status === 200) {
          const data = await response.json();
          console.log(data);
          showNotification();
        }
      };

      updateWithoutPic();
    } catch (error) {
      console.log(error);
    }
  } else {
    const file = profileImageUpload.files[0];
    try {
      const formData = new FormData();

      formData.append("file", file);
      formData.append("userId", localStorage.getItem("userId"));

      const uploadImage = async function () {
        const response = await fetch("http://localhost:8080/uploadImage", {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("userToken")}`,
          },
          body: formData,
        });

        const data = await response.json();

        return data.imgName;
      };

      const uploadUserProfile = async function () {
        console.log("Change");
        const imgName = await uploadImage();
        try {
          const response = await fetch("http://localhost:8080/updateWithPic", {
            method: "PUT",
            headers: {
              "Content-type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("userToken")}`,
            },
            body: JSON.stringify({
              displayName: displayName.value,
              bio: bio.value,
              location: locationOfUser.value,
              hobbies: hobbies.value,
              profilePicName: imgName,
              userProfileId: localStorage.getItem("userProfileId"),
            }),
          });

          console.log(response.status);

          if (response.status === 200) {
            showNotification();
            const data = await response.json();
            console.log(data);
          }
        } catch (error) {
          console.log(error.message);
        }
      };

      uploadUserProfile();
    } catch (error) {
      console.log(error);
    }
  }
});

function showNotification() {
  notification.style.bottom = "20px"; // Show the notification
  setTimeout(() => {
    hideNotification();
  }, 3000); // Hide the notification after 3 seconds
}

function hideNotification() {
  notification.style.bottom = "-100px"; // Hide the notification
}
