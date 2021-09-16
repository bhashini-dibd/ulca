export const menuItems = {
    dataset: [
        {
            name: 'My Contribution',
            url: '/dataset/my-contribution',
            roles:["CONTRIBUTOR-USER"]
        },
        {
            name: 'My Searches',
            url: '/my-searches',
            roles:["READONLY-USER","CONTRIBUTOR-USER"]
        },
        {
            name: 'Search & Download Records',
            url: '/search-and-download-rec/initiate/-1',
            roles:["CONTRIBUTOR-USER","READONLY-USER",]
        },
        // {
        //     name: 'Explore Readymade Datasets',
        //     url: '/readymade-dataset'
        // },
        {
            name: 'Submit Dataset',
            url: '/dataset/upload',
            roles:["CONTRIBUTOR-USER"]
        },
        // {
        //     name: 'Readymade Dataset',
        //     url: '/dataset/readymade-datasets',
        //     roles:["CONTRIBUTOR-USER"]
        // }
    ],

    models: [
        {
            name: 'My Contribution',
            url: '/model/my-contribution',
            roles:["CONTRIBUTOR-USER"]
        },
        {
            name: 'Explore Models',
            url: '/model/explore-models',
            roles:["READONLY-USER","CONTRIBUTOR-USER"]
        },
        {
            name: 'Submit Model',
            url: '/model/upload',
            roles:["CONTRIBUTOR-USER"]
        }],
    profile: [
        {
            name: 'Change Password',
            url: ''
        },
        {
            name: 'Feedback',
            url: ''
        },
        {
            name: 'Log out',
            url: '/user/login'
        }
    ]

}