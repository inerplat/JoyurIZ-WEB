import torch


class ModelManager:
    def __init__(self, model, path):
        self.model = model()
        self.model.load_state_dict(torch.load(path))
        self.model.eval()

    def predict(self, input):
        labels = ("Chaewon", "Yaena", "Yuri")
        outputs = self.model(input)
        predicted = torch.argmax(outputs)

        return labels[predicted]