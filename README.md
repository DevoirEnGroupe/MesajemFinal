# MESAJEM

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
Mesajem is an application that will allow you to transfer documents or receive documents from point A to another point.

### App Evaluation
- **Category:** Transport and delivery
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** The user can send a document from one place to another without having to move, by calling a delivery person, giving his location, and choosing his method of payment, or by going to deposit his parcel in a reception center while using the mobile application to track the document.

- **Market:** Any individual could choose to use this app, and to keep it a safe environment.
- **Habit:** the user can use the application as many times as he wants to send a document securely.
- **Scope:** User can use the application nationwide, transfer documents from one office to another and even to the receiver's home.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* The user will register or login if he already has an account.
* The user will have to choose the process he wants to start (send, received, or track).
* If the user has chosen to send a document, he will have to give details of this document just by taking a photo and then sending it. then share their location and finally pay.
* Choose the type of service (Express or not).
* if the user has chosen to track, he will be able to follow the status of his document (in Transit or arrive).
* If the user has received the document, he will have to click on received after he just has to take a photo and then send it.
* Taks page user


**Optional Nice-to-have Stories**

* A chat window to interact directly with the delivery person.
* A choice of insurance to protect the document

* A subscription plan and customer loyalty points

### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to get access to another option. 
   * ...

* Profile Screen 
   * Allows user to upload a photo and fill in information that is interesting.
* List Screen
  *user will choose the   taks(send, track, received)
* Screen Detail
  *user will take a picture
* Screen Localization
  *User will share localization
* Screen Payment
  *User will choose the method payment

   

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Send 
* Received 
* Track

Optional:
* Chat
* Assurance
* Subscription

**Flow Navigation** (Screen to Screen)
*  Login -> Account creation if no log in is available
* List Service ->
  •Send ->Document details->localization-payment method ->Home screen
  •Track
  •Received->document details
    
* Profile-> change Password,modify Mail, change picture.

## Wireframes
<img src="https://i.imgur.com/VL1TCAN.jpg?1" width=800><br>

### [BONUS] Digital Wireframes & Mockups
<img src="https://i.imgur.com/GL7ti93.jpg" height=200>

### [BONUS] Interactive Prototype
<img src="https://i.imgur.com/nqG1v8y.gif" width=200  height= 400>

<a href="https://i.imgur.com/nqG1v8y.gif">https://i.imgur.com/nqG1v8y.gif</a>



## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | client        | Pointer to User| image author |
   | image         | File     | image of the documents that user posts |
   | caption       | String   | image caption by author |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
   #### Delivery

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | Deliverman    | Pointer to User| image author |
   | emailVerified | String   | email of the user |
   | Adress        | String   | Living adress |
   | PhoneNum      | String   | Phone number of the user |
   | Status        | boolean  | Active and Desactive the user if|
   | Photos        | File     | Picture of the DeliveryMan |
   | Geolocal      | Pointer to User  | position of the deliveryman|
   | caption       | String   | image caption by author |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
   
      #### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | emailVerified | String   | email of the user |
   | Adress        | String   | Living adress |
   | PhoneNum      | String   | Phone number of the user |
   | Status        | boolean  | Active and Desactive the user if|
   | Photos        | File     | Picture of the DeliveryMan |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   
### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all posts where user is author
         ```swift
         let query = PFQuery(className:"Post")
         query.whereKey("author", equalTo: currentUser)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (posts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let posts = posts {
               print("Successfully retrieved \(posts.count) posts.")
           // TODO: Do something with posts...
            }
         }
         ```
      - (Create/POST) Create a new like on a post
      - (Delete) Delete existing like
      - (Create/POST) Create a new comment on a post
      - (Delete) Delete existing comment
   - Create Post Screen
      - (Create/POST) Create a new post object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image
      
      
