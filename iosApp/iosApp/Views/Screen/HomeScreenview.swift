import SwiftUI
import SpaceNewsKit

struct HomeScreenView: UIViewControllerRepresentable {

    private let screen: HomeScreen
    
    init(_ screen: HomeScreen) {
        self.screen = screen
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        HomeScreenUiController(screen: screen)
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}
