import torch


def torch_predict(model, path, input):
    labels = ("Chaewon", "Yaena", "Yuri")

    model.load_state_dict(torch.load(path))
    model.eval()

    outputs = model(input)
    predicted = torch.argmax(outputs)

    return labels[predicted]