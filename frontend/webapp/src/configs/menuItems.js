export const menuItems = {
  dataset: [
    {
      name: "My Contribution",
      url: "/dataset/my-contribution",
      roles: ["CONTRIBUTOR-USER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    {
      name: "My Searches",
      url: "/my-searches",
      roles: ["READONLY-USER", "CONTRIBUTOR-USER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    {
      name: "Search & Download Records",
      url: "/search-and-download-rec/initiate/-1",
      roles: ["CONTRIBUTOR-USER", "READONLY-USER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    // {
    //     name: 'Explore Readymade Datasets',
    //     url: '/readymade-dataset'
    // },
    {
      name: "Submit Dataset",
      url: "/dataset/upload",
      roles: ["CONTRIBUTOR-USER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    // {
    //     name: 'Readymade Dataset',
    //     url: '/dataset/readymade-datasets',
    //     roles:["CONTRIBUTOR-USER"]
    // }
  ],

  models: [
    {
      name: "My Contribution",
      url: "/model/my-contribution",
      roles: ["CONTRIBUTOR-USER", "EXTERNAL-CONSORTIUM-MEMBER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
    {
      name: "Explore Models",
      url: "/model/explore-models",
      roles: [
        "READONLY-USER",
        "CONTRIBUTOR-USER",
        "EXTERNAL-CONSORTIUM-MEMBER",
        "BENCHMARK-DATASET-CONTRIBUTOR"
      ],
      public: true,
    },
    // {
    //     name: 'Model Leaderboard',
    //     url: '/model/leaderboard',
    //     roles:["CONTRIBUTOR-USER"]
    // },
    {
      name: "Benchmark Datasets",
      url: "/model/benchmark-datasets",
      roles: ["CONTRIBUTOR-USER", "EXTERNAL-CONSORTIUM-MEMBER","BENCHMARK-DATASET-CONTRIBUTOR"],
      public: true,
    },
    {
      name: "Submit Model",
      url: "/model/upload",
      roles: ["CONTRIBUTOR-USER","EXTERNAL-CONSORTIUM-MEMBER","BENCHMARK-DATASET-CONTRIBUTOR"],
    },
  ],
  profile: [
    {
      name: "Change Password",
      url: "",
    },
    {
      name: "Feedback",
      url: "",
    },
    {
      name: "Log out",
      url: "/user/login",
    },
  ],
};
