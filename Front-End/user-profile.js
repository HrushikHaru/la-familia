const preloader = document.querySelector(".pre-loader");
const profileImage = document.getElementById("profile-image");
const profileImageUpload = document.getElementById("profile-image-upload");
const userProfileForm = document.querySelector("form");
const displayName = document.querySelector(".displayName");
const bio = document.querySelector(".bio");
const locationOfUser = document.querySelector(".location");
const hobbies = document.querySelector(".hobbies");

//Open and Close Modal
const modal = document.querySelector(".modal");
const overlay = document.querySelector(".overlay");
const modalClose = document.querySelector(".modalClose");
const validation = document.querySelector(".validation");
const errorMessage = document.querySelector(".validation-desc");

modalClose.addEventListener("click", function () {
  modal.classList.add("hidden");
  overlay.classList.add("hidden");
});

function showModalAndOverlay(validationMsg, validationDesc) {
  validation.textContent = "";
  errorMessage.textContent = "";

  validation.textContent = validationMsg;
  errorMessage.textContent = validationDesc;

  modal.classList.remove("hidden");
  overlay.classList.remove("hidden");
}

preloader.classList.add("show");
setTimeout(() => {
  preloader.classList.remove("show");
}, 1500);

const addProfile = function () {
  profileImageUpload.addEventListener("change", function (e) {
    e.preventDefault();

    const file = this.files[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = function () {
        profileImage.src = reader.result;
      };
      reader.readAsDataURL(file);

      const formData = new FormData();

      formData.append("file", file);
      formData.append("userId", localStorage.getItem("userId"));

      userProfileForm.addEventListener("submit", function (e) {
        e.preventDefault();

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
          const imgName = await uploadImage();
          try {
            const response = await fetch("http://localhost:8080/addUserInfo", {
              method: "POST",
              headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("userToken")}`,
              },
              body: JSON.stringify({
                profilePicName: imgName,
                displayName: displayName.value,
                bio: bio.value,
                location: locationOfUser.value,
                hobbies: hobbies.value,
                userId: localStorage.getItem("userId"),
              }),
            });

            console.log(response.status);

            if (response.status === 201) {
              window.location.href = "home-page.html";
            }
          } catch (error) {
            console.log(error.message);
          }
        };

        uploadUserProfile();
      });
    }
  });
};

addProfile();
