#The question : Can you write a Python code that counts the people in the video with OpenCV?

import cv2

# Load the video file
cap = cv2.VideoCapture('path_to_video_file.mp4')

# Initialize the person detection model
person_detector = cv2.HOGDescriptor()
person_detector.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())

# Initialize the person count
person_count = 0

while cap.isOpened():
    # Read each frame of the video
    ret, frame = cap.read()
    
    if ret:
        # Resize the frame to improve performance
        frame = cv2.resize(frame, (640, 480))
        
        # Detect people in the frame
        boxes, weights = person_detector.detectMultiScale(frame, winStride=(8,8))
        
        # Draw a rectangle around each detected person
        for box in boxes:
            cv2.rectangle(frame, (box[0], box[1]), (box[0]+box[2], box[1]+box[3]), (0,255,0), 2)
            
        # Update the person count
        person_count += len(boxes)
        
        # Display the frame and the person count
        cv2.putText(frame, f"Person count: {person_count}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
        cv2.imshow('frame', frame)
        
        # Wait for a key press to exit
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    else:
        break

# Release the video capture and close all windows
cap.release()
cv2.destroyAllWindows()
