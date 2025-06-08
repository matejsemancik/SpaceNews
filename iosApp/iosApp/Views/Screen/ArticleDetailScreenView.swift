import SwiftUI
import SpaceNewsKit

struct ArticleDetailScreenView: UIViewControllerRepresentable {

    private let screen: ArticleDetailScreen
    
    init(_ screen: ArticleDetailScreen) {
        self.screen = screen
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        ArticleDetailScreenUiController(screen: screen)
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}
