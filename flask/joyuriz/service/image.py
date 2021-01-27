from torchvision import transforms
from PIL import Image
import numpy as np
import face_recognition
import io
from joyuriz.error.handler import FaceError


class ImageManager:
    def __init__(self, input_image):
        self.raw_image = Image.open(io.BytesIO(input_image)).convert('RGB')
        self.image = np.asarray(self.raw_image)
        self.top = 0
        self.right = 0
        self.bottom = 0
        self.left = 0

    def crop_image(self):
        crop = self.image[self.top:self.bottom, self.left:self.right]
        self.image = Image.fromarray(crop).resize((256, 256))
        return self

    def find_face(self):
        faces = face_recognition.face_locations(self.image, number_of_times_to_upsample=0, model="hog")
        if len(faces) != 1:
            raise FaceError
        self.top, self.right, self.bottom, self.left = faces[0]
        return self

    def to_tensor(self):
        trans = transforms.Compose([
            transforms.ToTensor(),
            transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
        ])
        self.image = trans(self.image).unsqueeze_(0)
        return self

    def get_image(self):
        return self.image

    def get_face(self):
        return self.top, self.right, self.bottom, self.left
